package com.example.bbank.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbank.domain.models.News
import com.example.bbank.domain.use_cases.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor (
    private val getNewsUseCase: GetNewsUseCase
): ViewModel() {

    private val _news = MutableLiveData<List<News>>()
    val news: LiveData<List<News>> = _news

    init {
        viewModelScope.launch {
            fetchNews()
        }
    }

    private suspend fun fetchNews() {
        try {
            _news.value = getNewsUseCase.getNews()
        } catch (e: Exception) {
            _news.value = mutableListOf(News(e.message.toString(),"","","",""))
        }
    }
}