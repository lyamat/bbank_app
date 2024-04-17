package com.example.bbank.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.bbank.R
import com.example.bbank.databinding.NewsDetailDialogBinding
import com.example.bbank.domain.models.News

internal class NewsDetailDialog : DialogFragment() {
    private lateinit var chosenNews: News
    private lateinit var binding: NewsDetailDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
            dialog.window!!.setWindowAnimations(R.style.AppTheme_Slide)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewsDetailDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebView()
    }

    private fun setupWebView() {
        binding.apply {
            val webView: WebView = binding.newsDetailsWebView
            webView.settings.javaScriptEnabled = false
            webView.webViewClient = WebViewClient()
            webView.loadDataWithBaseURL(
                null,
                getString(R.string.html_image_style) + chosenNews.htmlRu,
                "text/html",
                "UTF-8",
                null
            )
        }
    }

    internal companion object {
        private const val TAG = "news_detail_dialog"
        fun display(fragmentManager: FragmentManager?, chosenNews: News): NewsDetailDialog {
            val exampleDialog = NewsDetailDialog()
            exampleDialog.chosenNews = chosenNews
            exampleDialog.show(fragmentManager!!, TAG)
            return exampleDialog
        }
    }
}