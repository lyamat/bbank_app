package com.example.bbank.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbank.R
import com.example.bbank.databinding.FragmentNewsBinding
import com.example.bbank.presentation.adapters.NewsAdapter
import com.example.core.domain.news.News
import com.example.core.presentation.ui.UiText
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
        observeNewsState()
    }

    private fun setupNewsRecyclerView() =
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = NewsAdapter(
                news = emptyList(),
                onClick = { news -> openNewsDetailFragment(news) }
            )
        }

    private fun observeNewsState() =
        viewLifecycleOwner.lifecycleScope.launch {
            newsViewModel.state
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    handleNewsState(it)
                }
        }

    private fun handleNewsState(state: NewsState) {
        if (state.news.isNotEmpty())
            (binding.rvNews.adapter as NewsAdapter).updateNews(state.news)

        state.error?.let {
            showError(state.error)
        }

        if (state.isLoading)
            showLoading()
        else hideLoading()
    }

    private fun openNewsDetailFragment(news: News) =
        Bundle().apply { putParcelable("newsParcelable", news.toNewsParcelable()) }.also {
            findNavController().navigate(
                R.id.action_newsFragment_to_newsDetailFragment, it
            )
        }

    private fun showError(messageError: UiText) =
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