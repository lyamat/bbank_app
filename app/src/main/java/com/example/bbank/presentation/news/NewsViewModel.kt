package com.example.bbank.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbank.domain.models.News
import com.example.bbank.domain.use_cases.local.DeleteAllLocalNewsUseCase
import com.example.bbank.domain.use_cases.local.GetLocalNewsUseCase
import com.example.bbank.domain.use_cases.local.SaveToLocalNewsUseCase
import com.example.bbank.domain.use_cases.remote.GetRemoteNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NewsViewModel @Inject constructor(
    private val getRemoteNewsUseCase: GetRemoteNewsUseCase,
    private val deleteAllLocalNewsUseCase: DeleteAllLocalNewsUseCase,
    private val saveToLocalNewsUseCase: SaveToLocalNewsUseCase,
    private val getLocalNewsUseCase: GetLocalNewsUseCase
) : ViewModel() {

    private val _newsFlow = MutableStateFlow<NewsEvent>(NewsEvent.Unspecified)
    internal fun newsFlow(): StateFlow<NewsEvent> = _newsFlow

    private var newsJob: Job? = null

    init {
        uploadLocalNews()
    }

    internal fun uploadRemoteNews() {
        newsJob = viewModelScope.launch {
            try {
                eventHolder(NewsEvent.Loading)
                val news = getRemoteNewsUseCase()
                deleteAllLocalNewsUseCase()
                saveToLocalNewsUseCase(news)
                eventHolder(NewsEvent.Success(news.take(5)))
            } catch (e: CancellationException) {
                eventHolder(NewsEvent.Error(e.message.toString()))
            } catch (e: Exception) {
                eventHolder(NewsEvent.Error(e.message.toString()))
            }
        }
    }

    private fun uploadLocalNews() =
        viewModelScope.launch {
            try {
                eventHolder(NewsEvent.Loading)
                val news = getLocalNewsUseCase()
                if (news.isEmpty()) uploadRemoteNews()
                else eventHolder(NewsEvent.Success(news.shuffled().take(5)))
            } catch (e: Exception) {
                eventHolder(NewsEvent.Error(e.message.toString()))
            }

        }

    private fun eventHolder(event: NewsEvent) = viewModelScope.launch {
        _newsFlow.emit(event)
    }

    internal fun cancelRequestForNews() {
        newsJob?.cancel()
        newsJob = null
    }

    internal sealed class NewsEvent {
        data object Unspecified : NewsEvent()
        data class Success(val news: List<News>) : NewsEvent()
        data class Error(val message: String) : NewsEvent()
        data object Loading : NewsEvent()
    }
}