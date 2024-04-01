package com.example.bbank.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.bbank.databinding.FragmentNewsBinding
import com.example.bbank.domain.models.News
import com.example.bbank.presentation.UiState
import dagger.hilt.android.AndroidEntryPoint
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
        observeNews()
    }

    private fun onStartNewsFragment() {
        binding.apply {
            btnGetRemoteNews.setOnClickListener {
                lifecycleScope.launch {
                    newsViewModel.fetchRemoteNews()
                }
            }

            btnGetLocalNews.setOnClickListener {
                lifecycleScope.launch {
                    newsViewModel.loadLocalNews()
                }
            }
        }
    }

    private fun observeNews() {
        newsViewModel.news.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    handleSuccess(it.data)
                    hideLoading()
                }
                is UiState.Error ->
                {
                    handleError(it.error)
                    hideLoading()
                }
                is UiState.Loading -> showLoading()
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
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    private fun handleSuccess(news: List<News>) {
        binding.apply {
            tvResultOutput.text  = news[(0..news.size).random()].nameRu
        }
    }


}