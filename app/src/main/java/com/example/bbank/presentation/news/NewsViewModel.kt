package com.example.bbank.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbank.domain.models.News
import com.example.bbank.domain.use_cases.GetLocalUseCase
import com.example.bbank.domain.use_cases.GetRemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NewsViewModel @Inject constructor(
    private val getRemoteUseCase: GetRemoteUseCase,
    private val getLocalUseCase: GetLocalUseCase,
) : ViewModel() {

    private val _newsFlow = MutableSharedFlow<NewsEvent>()
    internal fun newsFlow(): Flow<NewsEvent> = _newsFlow

    internal fun uploadRemoteNews() {
        viewModelScope.launch {
            // TODO: start progress bar
            try {
                eventHolder(NewsEvent.Loading)
                val news = getRemoteUseCase.getNews()
                getLocalUseCase.deleteAllLocalNews()
                getLocalUseCase.saveToLocalNews(news)
                eventHolder(NewsEvent.Success(news))
                // progress bar delay for about 4 sec
                // stop progress bar
            } catch (e: Exception) {
                eventHolder(NewsEvent.Error(e.message.toString()))
            }
        }
    }

    internal fun uploadLocalNews() {
        viewModelScope.launch {
            try {
                eventHolder(NewsEvent.Loading)
                val news = getLocalUseCase.getLocalNews()
                eventHolder(NewsEvent.Success(news))
            } catch (e: Exception) {
                eventHolder(NewsEvent.Error(e.message.toString()))
            }

        }
    }

    private fun eventHolder(event: NewsEvent) = viewModelScope.launch {
        _newsFlow.emit(event)
    }

    internal sealed class NewsEvent {
        data object Unspecified : NewsEvent()
        data class Success(val news: List<News>) : NewsEvent()
        data class Error(val message: String) : NewsEvent()
        data object Loading : NewsEvent()
    }
}

