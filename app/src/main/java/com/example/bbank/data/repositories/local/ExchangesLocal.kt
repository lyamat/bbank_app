package com.example.bbank.data.repositories.local

import com.example.bbank.data.local.exchanges.ExchangesEntity

internal interface ExchangesLocal {
    suspend fun getAllLocalExchanges(): List<ExchangesEntity>?
    suspend fun getLocalExchangeById(id: Long): ExchangesEntity?
    suspend fun getLocalExchangesByCity(cityName: String): List<ExchangesEntity>?
    suspend fun saveToLocalExchanges(exchangesEntity: ExchangesEntity)
    suspend fun deleteAllLocalExchanges(): Unit
}