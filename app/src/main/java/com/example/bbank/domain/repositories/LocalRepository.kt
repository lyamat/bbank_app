package com.example.bbank.domain.repositories

import com.example.bbank.data.local.exchanges.ExchangesEntity
import com.example.bbank.data.local.news.NewsEntity

internal interface LocalRepository {
    suspend fun getLocalNews(): List<NewsEntity>
    suspend fun getLocalNewsById(id: Long): NewsEntity
    suspend fun saveToLocalNews(newsEntity: NewsEntity)
    suspend fun deleteAllLocalNews(): Unit

    suspend fun getAllLocalExchanges(): List<ExchangesEntity>
    suspend fun getLocalExchangeById(id: Long): ExchangesEntity
    suspend fun getLocalExchangesByCity(cityName: String): List<ExchangesEntity>
    suspend fun saveToLocalExchanges(exchangesEntity: ExchangesEntity)
    suspend fun deleteAllLocalExchanges(): Unit

    suspend fun getCurrentCity(): String
}