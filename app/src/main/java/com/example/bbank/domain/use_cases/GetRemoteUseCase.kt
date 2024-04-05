package com.example.bbank.domain.use_cases

import com.example.bbank.data.remote.dto.toExchanges
import com.example.bbank.data.remote.dto.toNews
import com.example.bbank.domain.models.Exchanges
import com.example.bbank.domain.models.News
import com.example.bbank.domain.repositories.RemoteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetRemoteUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    suspend fun getExchanges(city: String): List<Exchanges> =
        remoteRepository.getRemoteExchangesByCity(city).map { exchangesDto ->
            exchangesDto.toExchanges()
        }

    suspend fun getNews(): List<News> =
        remoteRepository.getRemoteNews().map { newsDto ->
            newsDto.toNews()
        }
}