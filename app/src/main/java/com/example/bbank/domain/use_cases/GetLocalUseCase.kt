package com.example.bbank.domain.use_cases

import com.example.bbank.data.local.toNews
import com.example.bbank.domain.models.News
import com.example.bbank.domain.models.toNewsEntity
import com.example.bbank.domain.repositories.LocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetLocalUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend fun getLocalNews(): List<News> =
        localRepository.getLocalNews().map { newsEntity ->
            newsEntity.toNews()
        }

    suspend fun saveLocalNews(news: List<News>) =
        news.map { new -> localRepository.savePost(new.toNewsEntity()) }

}
