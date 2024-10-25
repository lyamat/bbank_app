package com.example.core.domain.converter

import kotlinx.coroutines.flow.Flow

interface ConverterRepository {
    fun getCurrencyRates(): Flow<List<DepartmentCurrencyRates>>
    suspend fun upsertCurrencyRates(departmentCurrencyRates: List<DepartmentCurrencyRates>)
    fun getConversionRates(): Flow<List<ConversionRate>>
    suspend fun upsertConversionRates(conversionRates: List<ConversionRate>)
    suspend fun getCurrencyValues(): List<Pair<String, String>>
    suspend fun setCurrencyValues(currencyValues: List<Pair<String, String>>)
}