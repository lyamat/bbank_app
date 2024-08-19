package com.example.bbank.domain.use_cases.local

import com.example.bbank.domain.models.News
import com.example.bbank.domain.models.toNewsEntity
import com.example.bbank.domain.repositories.LocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SaveToLocalNewsUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(news: List<News>) =
        news.forEach {
            localRepository.saveToLocalNews(it.toNewsEntity())
        }
}