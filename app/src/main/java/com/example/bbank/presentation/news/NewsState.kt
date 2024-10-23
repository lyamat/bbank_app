package com.example.bbank.presentation.news

import com.example.core.domain.news.News
import com.example.core.presentation.ui.UiText

internal data class NewsState(
    val news: List<News> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val isFetchCanceled: Boolean = false,
    val chosenNews: News? = null
)