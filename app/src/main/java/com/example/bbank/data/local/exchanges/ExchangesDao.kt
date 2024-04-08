package com.example.bbank.data.local.exchanges

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
internal interface ExchangesDao {
    @Query("SELECT * FROM exchanges")
    suspend fun getAllLocalExchanges(): List<ExchangesEntity>?

    @Query("SELECT * FROM exchanges WHERE id = :id")
    suspend fun getLocalExchangesById(id: Long): ExchangesEntity?

    @Query("SELECT * FROM exchanges WHERE name = :cityName")
    suspend fun getLocalExchangesByCity(cityName: String): List<ExchangesEntity>?

    @Insert
    suspend fun insertLocalExchange(newsEntity: ExchangesEntity)

    @Query("DELETE FROM news")
    suspend fun deleteAllLocalExchanges()
}
