package com.example.bbank.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbank.R
import com.example.bbank.databinding.FragmentNewsBinding
import com.example.bbank.domain.models.News
import com.example.bbank.presentation.adapters.NewsAdapter
import com.example.bbank.presentation.utils.UiText
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNewsRecyclerView()
        observeNewsUiState()
    }

    private fun setupNewsRecyclerView() =
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = NewsAdapter(
                news = emptyList(),
                onClick = { news -> openNewsDetailDialog(news) }
            )
        }

    private fun observeNewsUiState() =
        viewLifecycleOwner.lifecycleScope.launch {
            newsViewModel.newsUiState.collectLatest {
                handleNewsUiState(it)
            }
        }

    private fun handleNewsUiState(newsUiState: NewsUiState) {
        if (newsUiState.news.isNotEmpty())
            (binding.rvNews.adapter as NewsAdapter).updateNewsAdapterData(newsUiState.news)
        if (newsUiState.error != null) {
            handleError(newsUiState.error)
            newsViewModel.clearNewsStateError()
        }
        if (newsUiState.isLoading)
            showLoading()
        else hideLoading()
    }

    private fun openNewsDetailDialog(news: News) {
        val b = Bundle().apply { putParcelable("news", news) }
        findNavController().navigate(
            R.id.action_newsFragment_to_newsDetailFragment, b
        )
    }

    private fun handleError(messageError: UiText) =
        Snackbar.make(requireView(), messageError.asString(requireContext()), Snackbar.LENGTH_SHORT)
            .setAnchorView(R.id.bottomNavigation)
            .show()

    private fun hideLoading() {
        binding.apply {
            progressIndicatorNews.visibility = View.GONE
        }
    }

    private fun showLoading() {
        binding.apply {
            progressIndicatorNews.visibility = View.VISIBLE
        }
    }
}