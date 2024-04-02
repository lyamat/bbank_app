package com.example.bbank.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbank.domain.models.News
import com.example.bbank.domain.use_cases.GetLocalNewsUseCase
import com.example.bbank.domain.use_cases.GetRemoteNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getRemoteNewsUseCase: GetRemoteNewsUseCase,
    private val getLocalNewsUseCase: GetLocalNewsUseCase,
) : ViewModel() {

    private val _newsFlow: MutableStateFlow<NewsEvent> =
        MutableStateFlow(value = NewsEvent.Unspecified)

    internal fun newsFlow(): Flow<NewsEvent> = _newsFlow

    internal fun uploadRemoteNews() {
        viewModelScope.launch {
            try {
                eventHolder(NewsEvent.Loading)
                val news = getRemoteNewsUseCase.getNews()
                eventHolder(NewsEvent.Success(news))
            } catch (e: Exception) {
                eventHolder(NewsEvent.Error(e.message.toString()))
            }
        }
    }

    internal fun uploadLocalNews() {
        viewModelScope.launch {
            try {
                eventHolder(NewsEvent.Loading)
                val news = getLocalNewsUseCase.getLocalNews()
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

