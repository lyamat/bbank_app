package com.example.bbank.domain.use_cases

import com.example.bbank.data.remote.dto.toNews
import com.example.bbank.domain.models.News
import com.example.bbank.domain.repositories.RemoteRepository
import com.example.bbank.presentation.UiState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetRemoteNewsUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    suspend fun getNews(): UiState<List<News>> {
        val news = remoteRepository.getRemoteNews()?.map { newsDto ->
            newsDto.toNews()
        }
        if(news.isNullOrEmpty()) {
            return UiState.Error("Empty/null news list from server")
        }
        return UiState.Success(news)
    }
}