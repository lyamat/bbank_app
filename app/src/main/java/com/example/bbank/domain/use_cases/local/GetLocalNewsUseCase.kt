package com.example.bbank.domain.use_cases.local

import com.example.bbank.data.local.news.toNews
import com.example.bbank.domain.models.News
import com.example.bbank.domain.repositories.LocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetLocalNewsUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(): List<News> =
        localRepository.getLocalNews().map {
            it.toNews()
        }
}