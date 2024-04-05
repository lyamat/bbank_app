package com.example.bbank.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbank.R
import com.example.bbank.databinding.FragmentNewsBinding
import com.example.bbank.domain.models.News
import com.example.bbank.presentation.adapters.NewsAdapter
import com.example.bbank.presentation.utils.NewsDetailDialog
import com.example.bbank.presentation.utils.VerticalItemDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onStartNewsFragment()
        observeNewsEvent()

        setupNewsRv()
    }


    private fun onStartNewsFragment() {
        binding.apply {
            btnGetRemoteNews.setOnClickListener {
                newsViewModel.uploadRemoteNews()
            }

            btnGetLocalNews.setOnClickListener {
                newsViewModel.uploadLocalNews()
            }
        }
    }

    private fun openNewsDetailDialog(news: News) {
        NewsDetailDialog.display(getParentFragmentManager(), news)
    }

    private fun setupNewsRv() {
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = NewsAdapter(
                context = requireContext(),
                news = emptyList(),
                onClick = { news -> openNewsDetailDialog(news) }
            )
            addItemDecoration(VerticalItemDecoration())
        }
    }

    private fun observeNewsEvent() {
        CoroutineScope(Dispatchers.Main).launch {
            newsViewModel.newsFlow().collect {
                processNewsEvent(it)
            }
        }
    }

    private fun processNewsEvent(newsEvent: NewsViewModel.NewsEvent) {
        when (newsEvent) {
            is NewsViewModel.NewsEvent.Success -> {
                handleSuccess(newsEvent.news)
                hideLoading()
            }
            is NewsViewModel.NewsEvent.Error -> {
                handleError(newsEvent.message)
                hideLoading()
            }
            is NewsViewModel.NewsEvent.Loading -> {
                showLoading()
            }
            else -> Unit
        }
    }

    private fun handleSuccess(news: List<News>) {
        val shuffledNews = news.shuffled().take(5)
        (binding.rvNews.adapter as NewsAdapter).updateData(shuffledNews)
    }

    private fun handleError(error: String) {
        Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT)
            .setAnchorView(R.id.bottomNavigation)
            .show()
    }

    private fun hideLoading() {
        binding.progressIndicatorNews.visibility = View.GONE
        binding.btnGetLocalNews.visibility = View.VISIBLE
        binding.btnGetRemoteNews.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.progressIndicatorNews.visibility = View.VISIBLE
        binding.btnGetLocalNews.visibility = View.INVISIBLE
        binding.btnGetRemoteNews.visibility = View.INVISIBLE
    }
}