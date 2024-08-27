package com.example.bbank.domain.use_cases.local

import com.example.bbank.domain.repositories.NewsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DeleteAllLocalNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke() =
        newsRepository.deleteAllLocalNews()
}

