package com.example.bbank.presentation.news

import android.net.Uri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbank.R
import com.example.bbank.databinding.FragmentNewsBinding
import com.example.core.domain.news.NewsLink
import com.example.core.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class NewsFragment : BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate) {

    private val newsViewModel by activityViewModels<NewsViewModel>()

    override fun setupView() {
        setupNewsRecyclerView()
        observeNewsState()
    }

    override fun onClickButtonCancel() =
        newsViewModel.cancelCurrentFetching()

    private fun setupNewsRecyclerView() =
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = NewsAdapter(
                news = emptyList(),
                onClick = { newsLink -> openNewsDetailFragment(newsLink) }
            )
        }

    private fun openNewsDetailFragment(newsLink: NewsLink) {
        val deepLinkUri = Uri.parse("app://com.example.app/newsDetail?newsLink=$newsLink")

        val deepLinkRequest = NavDeepLinkRequest.Builder
            .fromUri(deepLinkUri)
            .build()

        findNavController().navigate(deepLinkRequest)
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
        if (state.news.isNotEmpty()) {
            binding.rvNews.adapter?.let { adapter ->
                if (adapter is NewsAdapter) {
                    adapter.updateNews(state.news)
                }
            }
        }

        if (state.isLoading) {
            showDialogProgressBar()
        } else {
            hideDialogProgressBar()
        }

        if (state.isFetchCanceled) {
            showDialogGeneralError(
                title = getString(R.string.what_happened),
                getString(R.string.request_was_canceled)
            )
            newsViewModel.setIsFetchCanceled(false)
        }

        state.error?.let {
            showDialogGeneralError(
                title = getString(R.string.error_occurred),
                it.asString(requireContext())
            )
            newsViewModel.setStateError(null)
        }
    }
}