package com.example.core.data.converter

import com.example.core.domain.converter.ConversionRate
import com.example.core.domain.converter.ConverterRepository
import com.example.core.domain.converter.DepartmentCurrencyRates
import com.example.core.domain.converter.LocalConverterDataSource
import com.example.core.domain.shared_preferences.SharedPreferencesLocal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConverterRepositoryImpl @Inject constructor(
    private val localConverterDataSource: LocalConverterDataSource,
    private val sharedPreferencesLocal: SharedPreferencesLocal
) : ConverterRepository {
    override fun getCurrencyRates(): Flow<List<DepartmentCurrencyRates>> {
        return localConverterDataSource.getCurrencyRates()
    }

    override suspend fun upsertCurrencyRates(departmentCurrencyRates: List<DepartmentCurrencyRates>) {
        localConverterDataSource.upsertCurrencyRates(departmentCurrencyRates)
    }

    override fun getConversionRates(): Flow<List<ConversionRate>> {
        return localConverterDataSource.getConversionRates()
    }

    override suspend fun upsertConversionRates(conversionRates: List<ConversionRate>) {
        localConverterDataSource.upsertConversionRates(conversionRates)
    }

    override suspend fun getCurrencyValues(): List<Pair<String, String>> {
        return sharedPreferencesLocal.getCurrencyValues()
    }

    override suspend fun setCurrencyValues(currencyValues: List<Pair<String, String>>) {
        sharedPreferencesLocal.setCurrencyValues(currencyValues)
    }
}