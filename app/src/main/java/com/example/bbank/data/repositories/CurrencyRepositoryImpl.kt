package com.example.bbank.data.repositories

import com.example.bbank.data.local.currency_rates.CurrencyRatesEntity
import com.example.bbank.data.repositories.local.CurrencyRatesLocal
import com.example.bbank.data.repositories.local.SharedPreferencesLocal
import com.example.bbank.domain.repositories.CurrencyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class CurrencyRepositoryImpl @Inject constructor(
    private val currencyRatesLocal: CurrencyRatesLocal,
    private val sharedPreferencesLocal: SharedPreferencesLocal
) : CurrencyRepository {
    override suspend fun getLocalCurrencyRates(): List<CurrencyRatesEntity> =
        currencyRatesLocal.getLocalCurrencyRates() ?: listOf(CurrencyRatesEntity.empty())

    override suspend fun saveToLocalCurrencyRates(currencyRatesEntity: CurrencyRatesEntity) =
        currencyRatesLocal.saveToLocalCurrencyRates(currencyRatesEntity)

    override suspend fun deleteAllCurrencyRates() =
        currencyRatesLocal.deleteAllLocalCurrencyRates()

    override suspend fun getCurrencyValues(): List<Pair<String, String>> =
        sharedPreferencesLocal.getCurrencyValues()

    override suspend fun setCurrencyValues(currencyValues: List<Pair<String, String>>) {
        sharedPreferencesLocal.setCurrencyValues(currencyValues)
    }
}
