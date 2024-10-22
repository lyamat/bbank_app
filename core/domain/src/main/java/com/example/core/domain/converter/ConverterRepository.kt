package com.example.core.domain.converter

import kotlinx.coroutines.flow.Flow

interface ConverterRepository {
    fun getCurrencyRates(): Flow<List<CurrencyRates>>
    suspend fun upsertCurrencyRates(currencyRates: List<CurrencyRates>)
    fun getConversionRates(): Flow<List<ConversionRate>>
    suspend fun upsertConversionRates(conversionRates: List<ConversionRate>)
    suspend fun getCurrencyValues(): List<Pair<String, String>>
    suspend fun setCurrencyValues(currencyValues: List<Pair<String, String>>)
}