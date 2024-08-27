package com.example.bbank.domain.repositories

import com.example.bbank.data.local.currency_rates.CurrencyRatesEntity

internal interface CurrencyRepository {
    suspend fun getLocalCurrencyRates(): List<CurrencyRatesEntity>
    suspend fun saveToLocalCurrencyRates(currencyRatesEntity: CurrencyRatesEntity)
    suspend fun deleteAllCurrencyRates()

    suspend fun getCurrencyValues(): List<Pair<String, String>>
    suspend fun setCurrencyValues(currencyValues: List<Pair<String, String>>)
}
