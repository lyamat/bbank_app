package com.example.bbank.presentation.news

import androidx.lifecycle.ViewModel
import com.example.core.domain.news.News
import com.example.core.domain.news.NewsLink
import com.example.core.domain.news.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class NewsDetailViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val _state = MutableStateFlow(NewsState())
    val state: StateFlow<NewsState> get() = _state

    fun getNewsByLink(newsLink: NewsLink) {
        newsRepository.getNewsByLink(newsLink).onEach {
            setChosenNews(it)
        }.launchIn(viewModelScope)
    }

    private fun setChosenNews(newsResult: News) =
        _state.update { NewsState(chosenNews = newsResult) }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}