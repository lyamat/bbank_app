package com.example.bbank.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbank.domain.models.News
import com.example.bbank.domain.use_cases.GetLocalNewsUseCase
import com.example.bbank.domain.use_cases.GetRemoteNewsUseCase
import com.example.bbank.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor (
    private val getRemoteNewsUseCase: GetRemoteNewsUseCase,
    private val getLocalNewsUseCase: GetLocalNewsUseCase
): ViewModel() {

    private val _news = MutableLiveData<UiState<List<News>>>()
    val news: LiveData<UiState<List<News>>> = _news

//    private val _localNewsSize = MutableLiveData<Int>()
//    val localNewsSize: LiveData<Int> = _localNewsSize

    init {
        viewModelScope.launch {
//            fetchRemoteNews()
//            fetchLocalNews()
        }
    }

    suspend fun fetchRemoteNews() {
        viewModelScope.launch {
            _news.value = UiState.Loading
            try {
                _news.value = getRemoteNewsUseCase.getNews()
            } catch (e: Exception) {
                _news.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun loadLocalNews() {
        viewModelScope.launch {
            _news.value = UiState.Loading
            try {
                _news.value = getLocalNewsUseCase.getLocalNews()
            } catch (e: Exception) {
                _news.value = UiState.Error("Error fetching local news: ${e.message}")
            }
        }
    }
}