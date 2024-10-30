package com.example.news.presentation

import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.core.domain.news.News
import com.example.core.presentation.ui.base.BaseFragment
import com.example.news.presentation.databinding.FragmentNewsDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class NewsDetailFragment :
    BaseFragment<FragmentNewsDetailBinding>(FragmentNewsDetailBinding::inflate) {

    private val newsDetailViewModel by viewModels<NewsDetailViewModel>()

    override fun setupView() {
        getNewsLinkFromArgs()
        observeNewsDetailState()
    }

    private fun getNewsLinkFromArgs() {
        arguments?.getString("newsLink")?.let {
            getNewsByLink(it)
        }
    }

    private fun getNewsByLink(newsLink: String) {
        newsDetailViewModel.getNewsByLink(newsLink)
    }

    private fun setupWebViewWithChosenNews(chosenNews: News) =
        binding.newsDetailsWebView.apply {
            settings.javaScriptEnabled = false
            webViewClient = WebViewClient()
            loadDataWithBaseURL(
                null,
                getString(R.string.html_image_style) + chosenNews.htmlRu,
                getString(R.string.text_html),
                getString(R.string.utf_8),
                null
            )
        }

    private fun setToolbarTitle(chosenNews: News) {
//        requireActivity().actionBar?.title = getString(R.string.news_of, chosenNews.startDate)
//        (activity as? MainActivity)?.supportActionBar?.title =
//            getString(R.string.news_of, chosenNews.startDate)
    }

    private fun observeNewsDetailState() =
        viewLifecycleOwner.lifecycleScope.launch {
            newsDetailViewModel.state
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest { state ->
                    handleNewsState(state)
                }
        }

    private fun handleNewsState(state: NewsState) {
        state.chosenNews?.let {
            setupWebViewWithChosenNews(it)
            setToolbarTitle(it)
        }
    }

    override fun onClickButtonCancel() = Unit
}