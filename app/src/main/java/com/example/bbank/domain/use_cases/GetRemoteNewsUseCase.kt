package com.example.bbank.domain.use_cases

import com.example.bbank.data.remote.dto.toNews
import com.example.bbank.domain.models.News
import com.example.bbank.domain.repositories.RemoteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetRemoteNewsUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    suspend fun getNews() : List<News> =
        remoteRepository.getRemoteNews().map {newsDto ->
            newsDto.toNews()
    }
}