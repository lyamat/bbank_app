package com.example.bbank.presentation.news

import androidx.lifecycle.ViewModel
import com.example.core.domain.news.NewsRepository
import com.example.core.domain.news.SyncNewsScheduler
import com.example.core.domain.util.Result
import com.example.core.presentation.ui.UiText
import com.example.core.presentation.ui.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration.Companion.hours

@HiltViewModel
internal class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val syncNewsScheduler: SyncNewsScheduler
) : ViewModel() {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val _state = MutableStateFlow(NewsState())
    val state: StateFlow<NewsState> get() = _state

    init {
        newsRepository.getNews().onEach { news ->
            if (news.isEmpty()) {
                fetchNews()
            } else {
                _state.update { NewsState(news = news.take(5)) }
            }
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            syncNewsScheduler.scheduleSync(
                SyncNewsScheduler.SyncType.FetchNews(24.hours)
            )
        }
    }

    private fun fetchNews() {
        viewModelScope.launch {
            try {
                setStateIsLoading(true)
                when (val result = newsRepository.fetchNews()) {
                    is Result.Error -> {
                        setStateError(result.error.asUiText())
                    }

                    is Result.Success -> {
                        Unit
                    }
                }
            } catch (e: CancellationException) {
                setIsFetchCanceled(true)
            } finally {
                setStateIsLoading(false)
            }
        }
    }

    fun setIsFetchCanceled(isFetchCanceled: Boolean) =
        _state.update { it.copy(isFetchCanceled = isFetchCanceled) }

    fun setStateError(uiText: UiText?) =
        _state.update { it.copy(error = uiText) }

    private fun setStateIsLoading(isLoading: Boolean) =
        _state.update { it.copy(isLoading = isLoading) }

    fun cancelCurrentFetching() {
        viewModelJob.cancelChildren()
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}