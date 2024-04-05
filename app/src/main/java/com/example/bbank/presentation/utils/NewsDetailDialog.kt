package com.example.bbank.presentation.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.bbank.R
import com.example.bbank.databinding.NewsDetailDialogBinding
import com.example.bbank.domain.models.News

internal class NewsDetailDialog : DialogFragment() {
    private var toolbar: Toolbar? = null
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
        binding.apply {
            tbNewsDetail.setNavigationOnClickListener { dismiss() }
            tbNewsDetail.setTitle(chosenNews.nameRu) // title from news object
            tbNewsDetail.setOnMenuItemClickListener {
                dismiss()
                true
            }

            Glide.with(view).load(chosenNews.img).into(ivNewsDetailImage)
            tvNewsDetailDate.text = chosenNews.startDate
            tvNewsDetailLink.text = chosenNews.link
            tvNewsDetailText.text = chosenNews.htmlRu
        }

    }

    internal companion object {
        const val TAG = "news_detail_dialog"
        fun display(fragmentManager: FragmentManager?, chosenNews: News): NewsDetailDialog {
            val exampleDialog = NewsDetailDialog()
            exampleDialog.chosenNews = chosenNews
            exampleDialog.show(fragmentManager!!, TAG)
            return exampleDialog
        }
    }
}