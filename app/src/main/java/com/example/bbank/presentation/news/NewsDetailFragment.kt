package com.example.bbank.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.bbank.R
import com.example.bbank.databinding.FragmentNewsDetailBinding
import com.example.bbank.presentation.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class NewsDetailFragment : Fragment() {
    private val args by navArgs<NewsDetailFragmentArgs>()
    private lateinit var binding: FragmentNewsDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebView()
        setToolbarTitle()
    }

    private fun setupWebView() =
        binding.newsDetailsWebView.apply {
            this.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            settings.javaScriptEnabled = false
            webViewClient = WebViewClient()
            loadDataWithBaseURL(
                null,
                getString(R.string.html_image_style) + args.news.htmlRu,
                getString(R.string.text_html),
                getString(R.string.utf_8),
                null
            )
        }

    private fun setToolbarTitle() {
        (activity as MainActivity).supportActionBar?.title =
            getString(R.string.news_of, args.news.startDate)
    }
}