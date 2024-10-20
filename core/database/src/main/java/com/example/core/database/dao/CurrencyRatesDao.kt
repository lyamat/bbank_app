package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.core.database.entity.CurrencyRatesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyRatesDao {
    @Upsert
    suspend fun upsertCurrencyRates(currencyRates: List<CurrencyRatesEntity>)

    @Query("SELECT * FROM currencyratesentity")
    fun getCurrencyRates(): Flow<List<CurrencyRatesEntity>>
}