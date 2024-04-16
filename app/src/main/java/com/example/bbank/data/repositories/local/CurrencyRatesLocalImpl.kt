package com.example.bbank.data.repositories.local

import com.example.bbank.data.local.currency_rates.CurrencyRatesDao
import com.example.bbank.data.local.currency_rates.CurrencyRatesEntity
import javax.inject.Inject

internal class CurrencyRatesLocalImpl @Inject constructor(
    private val currencyRatesDao: CurrencyRatesDao
) : CurrencyRatesLocal {
    override suspend fun getLocalCurrencyRates(): List<CurrencyRatesEntity>? =
        currencyRatesDao.getAllCurrencyRates()

    override suspend fun saveToLocalCurrencyRates(currencyRatesEntity: CurrencyRatesEntity) =
        currencyRatesDao.insertCurrencyRates(currencyRatesEntity)

    override suspend fun deleteAllLocalCurrencyRates() =
        currencyRatesDao.deleteAllCurrencyRates()
}