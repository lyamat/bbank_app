package com.example.bbank.data.repositories.local

import com.example.bbank.data.local.exchanges.ExchangesDao
import com.example.bbank.data.local.exchanges.ExchangesEntity
import javax.inject.Inject

internal class ExchangesLocalImpl @Inject constructor(
    private val exchangesDao: ExchangesDao
) : ExchangesLocal {
    override suspend fun getAllLocalExchanges(): List<ExchangesEntity>? =
        exchangesDao.getAllLocalExchanges()

    override suspend fun getLocalExchangeById(id: Long): ExchangesEntity? =
        exchangesDao.getLocalExchangesById(id)

    override suspend fun getLocalExchangesByCity(cityName: String): List<ExchangesEntity>? =
        exchangesDao.getLocalExchangesByCity(cityName)

    override suspend fun saveToLocalExchanges(exchangesEntity: ExchangesEntity) {
        exchangesDao.insertLocalExchange(exchangesEntity)
    }

    override suspend fun deleteAllLocalExchanges() {
        exchangesDao.deleteAllLocalExchanges()
    }
}