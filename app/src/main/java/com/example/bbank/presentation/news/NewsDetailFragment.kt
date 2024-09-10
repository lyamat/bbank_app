package com.example.bbank.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bbank.R
import com.example.bbank.databinding.DialogNewsDetailBinding
import com.example.bbank.domain.models.News
import com.example.bbank.presentation.departments.DepartmentDetailsFragmentArgs

internal class NewsDetailDialog : DialogFragment() {
    private val args by navArgs<NewsDetailDialogArgs>()

    private lateinit var binding: DialogNewsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebView()
        setOnBackPressedButton()
    }

    private fun setOnBackPressedButton() =
        binding.tbNewsDetail.setNavigationOnClickListener {
            findNavController().
        }

    private fun setupWebView() =
        binding.apply {
            val webView: WebView = binding.newsDetailsWebView
            webView.settings.javaScriptEnabled = false
            webView.webViewClient = WebViewClient()
            webView.loadDataWithBaseURL(
                null,
                getString(R.string.html_image_style) + args.news.htmlRu,
                getString(R.string.text_html),
                getString(R.string.utf_8),
                null
            )
        }
}