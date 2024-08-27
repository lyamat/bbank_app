package com.example.bbank.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbank.domain.models.News
import com.example.bbank.domain.networking.Result
import com.example.bbank.domain.use_cases.local.DeleteAllLocalNewsUseCase
import com.example.bbank.domain.use_cases.local.GetLocalNewsUseCase
import com.example.bbank.domain.use_cases.local.SaveToLocalNewsUseCase
import com.example.bbank.domain.use_cases.remote.GetRemoteNewsUseCase
import com.example.bbank.presentation.utils.UiText
import com.example.bbank.presentation.utils.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NewsViewModel @Inject constructor(
    private val getRemoteNewsUseCase: GetRemoteNewsUseCase,
    private val deleteAllLocalNewsUseCase: DeleteAllLocalNewsUseCase,
    private val saveToLocalNewsUseCase: SaveToLocalNewsUseCase,
    private val getLocalNewsUseCase: GetLocalNewsUseCase
) : ViewModel() {

    private val _newsUiState = MutableStateFlow(NewsUiState())
    val newsUiState: StateFlow<NewsUiState> get() = _newsUiState

    init {
        fetchLocalNews()
    }

    internal fun fetchRemoteNews() {
        viewModelScope.launch {
            _newsUiState.update { it.copy(isLoading = true) }
            when (val result = getRemoteNewsUseCase()) {
                is Result.Success -> processSuccessNews(result.data)
                is Result.Error -> updateNewsStateWithError(result.error.asUiText())
            }
        }
    }

    private suspend fun processSuccessNews(news: List<News>) {
        deleteAllLocalNewsUseCase()
        saveToLocalNewsUseCase(news)
        _newsUiState.value = NewsUiState(news = news.take(5))
    }

    private fun fetchLocalNews() =
        viewModelScope.launch {
            _newsUiState.update { it.copy(isLoading = true) }
            try {
                val news = getLocalNewsUseCase()
                if (news.isEmpty()) {
                    fetchRemoteNews()
                } else {
                    _newsUiState.update { NewsUiState(news = news.shuffled().take(5)) }
                }
            } catch (e: Exception) {
                updateNewsStateWithError(e)
            }
        }

    private fun updateNewsStateWithError(uiText: UiText) =
        _newsUiState.update {
            it.copy(
                error = uiText,
                isLoading = false
            )
        }

    private fun updateNewsStateWithError(e: Exception) =
        _newsUiState.update {
            it.copy(
                error = e.message?.let { message -> UiText.DynamicString(message) },
                isLoading = false
            )
        }

    internal fun clearNewsStateError() {
        _newsUiState.value = _newsUiState.value.copy(error = null)
    }
}