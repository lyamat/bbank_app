package com.example.bbank.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbank.domain.models.News
import com.example.bbank.domain.use_cases.GetLocalNewsUseCase
import com.example.bbank.domain.use_cases.GetRemoteNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor (
    private val getRemoteNewsUseCase: GetRemoteNewsUseCase,
    private val getLocalNewsUseCase: GetLocalNewsUseCase
): ViewModel() {

    private val _news = MutableLiveData<List<News>>()
    val news: LiveData<List<News>> = _news

    private val _localNewsSize = MutableLiveData<Int>()
    val localNewsSize: LiveData<Int> = _localNewsSize

    init {
        viewModelScope.launch {
            fetchRemoteNews()
//            fetchLocalNewById()
        }
    }

    private suspend fun fetchRemoteNews() {
        try {
            _news.value = getRemoteNewsUseCase.getNews()
            _localNewsSize.value = getLocalNewsUseCase.getLocalNews().size
        } catch (e: Exception) {
            _news.value = mutableListOf(News(e.message.toString(), "", "", "", ""))
        }
    }
}