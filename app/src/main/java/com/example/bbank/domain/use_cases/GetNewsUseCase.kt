package com.example.bbank.domain.use_cases

import com.example.bbank.domain.models.News
import com.example.bbank.domain.repositories.RemoteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetNewsUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    suspend fun getNews() : List<News> {
        return remoteRepository.getNews()
    }
}