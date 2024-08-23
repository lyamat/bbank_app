package com.example.bbank.presentation.news

import com.example.bbank.domain.models.News
import com.example.bbank.presentation.utils.UiText

internal data class NewsUiState(
    val news: List<News> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null
)