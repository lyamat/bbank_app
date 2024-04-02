package com.example.bbank.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.bbank.R
import com.example.bbank.databinding.FragmentNewsBinding
import com.example.bbank.domain.models.News
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {
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
    }


    private fun onStartNewsFragment() {
        binding.apply {
            btnGetRemoteNews.setOnClickListener {
                lifecycleScope.launch {
                    newsViewModel.uploadRemoteNews()
                }
            }

            btnGetLocalNews.setOnClickListener {
                lifecycleScope.launch {
                    newsViewModel.uploadLocalNews()
                }
            }
        }
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnGetLocalNews.visibility = View.VISIBLE
        binding.btnGetRemoteNews.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnGetLocalNews.visibility = View.INVISIBLE
        binding.btnGetRemoteNews.visibility = View.INVISIBLE
    }

    private fun handleError(error: String) {
        Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT)
            .setAnchorView(R.id.bottomNavigation)
            .show()
    }

    private fun handleSuccess(news: List<News>) {
        binding.apply {
            tvResultOutput.text = news[(0..news.size).random()].nameRu
        }
    }

    private fun observeNewsEvent() { // TODO: observer
        CoroutineScope(Dispatchers.Main).launch {
            newsViewModel.newsFlow().collect {
                when (it) {
                    is NewsViewModel.NewsEvent.Success -> {
                        handleSuccess(it.news)
                        hideLoading()
                    }
                    is NewsViewModel.NewsEvent.Error -> {
                        handleError(it.message)
                        hideLoading()
                    }
                    is NewsViewModel.NewsEvent.Loading -> {
                        showLoading()
                    }
                    else -> Unit
                }
            }
        }
    }
}