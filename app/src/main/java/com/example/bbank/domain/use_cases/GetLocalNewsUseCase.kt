package com.example.bbank.domain.use_cases

import com.example.bbank.data.local.toNews
import com.example.bbank.domain.models.News
import com.example.bbank.domain.repositories.LocalRepository
import com.example.bbank.presentation.UiState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLocalNewsUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend fun getLocalNews(): UiState<List<News>> {
        val newsEntities = localRepository.getLocalNews()
        val news = newsEntities?.map { newsEntity ->
            newsEntity.toNews()
        }
        return if (!news.isNullOrEmpty()) {
            UiState.Success(news)
        } else {
            UiState.Error("Empty database")
        }
    }

}