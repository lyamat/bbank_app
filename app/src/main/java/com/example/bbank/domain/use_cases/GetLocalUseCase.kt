package com.example.bbank.domain.use_cases

import com.example.bbank.data.local.exchanges.toExchanges
import com.example.bbank.data.local.news.toNews
import com.example.bbank.domain.models.Exchanges
import com.example.bbank.domain.models.News
import com.example.bbank.domain.models.toExchangesEntity
import com.example.bbank.domain.models.toNewsEntity
import com.example.bbank.domain.repositories.LocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetLocalUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend fun getLocalNews(): List<News> =
        localRepository.getLocalNews().map {
            it.toNews()
        }

    suspend fun saveToLocalNews(news: List<News>) =
        news.map {
            localRepository.saveToLocalNews(it.toNewsEntity())
        }

    suspend fun getLocalExchangesByCity(cityName: String): List<Exchanges> =
        localRepository.getLocalExchangesByCity(cityName).map {
            it.toExchanges()
        }

    suspend fun saveToLocalExchanges(exchanges: List<Exchanges>) =
        exchanges.map {
            localRepository.saveToLocalExchanges(it.toExchangesEntity())
        }
}
