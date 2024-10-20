package com.example.core.domain.converter

import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

typealias CurrencyRatesId = String
typealias ConversionRatesId = Int

interface LocalConverterDataSource {
    suspend fun upsertCurrencyRates(currencyRates: List<CurrencyRates>): Result<List<CurrencyRatesId>, DataError.Local>
    fun getCurrencyRates(): Flow<List<CurrencyRates>>

    suspend fun upsertConversionRates(conversionRates: List<ConversionRate>): Result<List<ConversionRatesId>, DataError.Local>
    fun getConversionRates(): Flow<List<ConversionRate>>
}