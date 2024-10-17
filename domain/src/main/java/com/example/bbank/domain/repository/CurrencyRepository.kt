package com.example.bbank.domain.repository

import com.example.bbank.domain.models.CurrencyRates
import com.example.bbank.domain.models.Department

interface CurrencyRepository {
    suspend fun getLocalCurrencyRates(): List<CurrencyRates>
    suspend fun saveToLocalCurrencyRates(department: Department)
    suspend fun deleteAllCurrencyRates()
    suspend fun getLocalCurrencyValues(): List<Pair<String, String>>
    suspend fun setLocalCurrencyValues(currencyValues: List<Pair<String, String>>)
}
