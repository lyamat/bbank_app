package com.example.bbank.data.repositories

import com.example.bbank.data.local.exchanges.ExchangesEntity
import com.example.bbank.data.local.news.NewsEntity
import com.example.bbank.data.repositories.local.ExchangesLocal
import com.example.bbank.data.repositories.local.NewsLocal
import com.example.bbank.data.repositories.local.SharedPreferencesLocal
import com.example.bbank.domain.repositories.LocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class LocalRepositoryImpl @Inject constructor(
    private val newsLocal: NewsLocal,
    private val exchangesLocal: ExchangesLocal,
    private val sharedPreferencesLocal: SharedPreferencesLocal
) : LocalRepository {
    override suspend fun getLocalNews(): List<NewsEntity> =
        newsLocal.getLocalNews() ?: listOf(NewsEntity.empty())

    override suspend fun getLocalNewsById(id: Long): NewsEntity =
        newsLocal.getLocalNewsById(id = id) ?: NewsEntity.empty()

    override suspend fun saveToLocalNews(newsEntity: NewsEntity) =
        newsLocal.saveToLocalNews(newsEntity)

    override suspend fun deleteAllLocalNews() {
        newsLocal.deleteAllLocalNews()
    }

    override suspend fun getAllLocalExchanges(): List<ExchangesEntity> =
        exchangesLocal.getAllLocalExchanges() ?: listOf(ExchangesEntity.empty())

    override suspend fun getLocalExchangeById(id: Long): ExchangesEntity =
        exchangesLocal.getLocalExchangeById(id) ?: ExchangesEntity.empty()

    override suspend fun getLocalExchangesByCity(cityName: String): List<ExchangesEntity> =
        if (cityName.isBlank()) {
            exchangesLocal.getAllLocalExchanges() ?: listOf(ExchangesEntity.empty())
        } else {
            exchangesLocal.getLocalExchangesByCity(cityName) ?: listOf(ExchangesEntity.empty())
        }

    override suspend fun saveToLocalExchanges(exchangesEntity: ExchangesEntity) =
        exchangesLocal.saveToLocalExchanges(exchangesEntity)

    override suspend fun deleteAllLocalExchanges() =
        exchangesLocal.deleteAllLocalExchanges()

    override suspend fun getCurrentCity(): String =
        sharedPreferencesLocal.getCurrentCity()

}