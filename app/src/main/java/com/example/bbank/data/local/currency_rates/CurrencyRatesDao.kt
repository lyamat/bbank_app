package com.example.bbank.data.local.currency_rates

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
internal interface CurrencyRatesDao {
    @Query("SELECT * FROM currency_rates")
    suspend fun getAllCurrencyRates(): List<CurrencyRatesEntity>?

    @Query("SELECT * FROM currency_rates WHERE id = :id")
    suspend fun getCurrencyRatesById(id: Long): CurrencyRatesEntity?

    @Insert
    suspend fun insertCurrencyRates(currencyRatesEntity: CurrencyRatesEntity)

    @Query("DELETE FROM currency_rates")
    suspend fun deleteAllCurrencyRates()
}

