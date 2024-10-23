package com.example.bbank.presentation.news

import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.bbank.R
import com.example.bbank.databinding.FragmentNewsDetailBinding
import com.example.bbank.presentation.activity.MainActivity
import com.example.core.domain.news.News
import com.example.core.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class NewsDetailFragment :
    BaseFragment<FragmentNewsDetailBinding>(FragmentNewsDetailBinding::inflate) {
    private val args by navArgs<NewsDetailFragmentArgs>()
    private val newsViewModel by activityViewModels<NewsViewModel>()

    override fun setupView() {
        getNewsByIdFromArgs()
        observeNewsState()
    }

    private fun getNewsByIdFromArgs() {
        newsViewModel.getNewsByLink(args.newsLink)
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
        (activity as? MainActivity)?.supportActionBar?.title =
            getString(R.string.news_of, chosenNews.startDate)
    }

    private fun observeNewsState() =
        viewLifecycleOwner.lifecycleScope.launch {
            newsViewModel.state
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