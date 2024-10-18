package com.example.bbank.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.news.News
import com.example.core.domain.news.NewsRepository
import com.example.core.domain.news.SyncNewsScheduler
import com.example.core.domain.util.Result
import com.example.core.presentation.ui.UiText
import com.example.core.presentation.ui.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours

@HiltViewModel
internal class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val syncNewsScheduler: SyncNewsScheduler
) : ViewModel() {

    private val _state = MutableStateFlow(NewsState())
    val state: StateFlow<NewsState> get() = _state

    init {
        viewModelScope.launch {
            syncNewsScheduler.scheduleSync(
                type = SyncNewsScheduler.SyncType.FetchNews(24.hours)
            )
        }

        newsRepository.getNews().onEach { news ->
            _state.update { it.copy(news = news.shuffled().take(5)) }
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            newsRepository.fetchNews()
        }
    }

    private fun fetchNews() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = newsRepository.fetchNews()) {
                is Result.Success -> Unit
                is Result.Error -> updateNewsStateWithError(result.error.asUiText())
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private suspend fun processSuccessNews(news: List<News>) {
        _state.value = NewsState(news = news.take(5))
    }

    private fun updateNewsStateWithError(uiText: UiText) =
        _state.update {
            it.copy(error = uiText)
        }

    internal fun clearNewsStateError() {
        _state.value = _state.value.copy(error = null)
    }
}