package com.example.bbank.domain.use_cases.local

import com.example.bbank.data.local.currency_rates.toCurrencyRates
import com.example.bbank.domain.models.CurrencyRates
import com.example.bbank.domain.repositories.CurrencyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetLocalCurrencyRatesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {
    suspend operator fun invoke(): List<CurrencyRates> =
        currencyRepository.getLocalCurrencyRates().map {
            it.toCurrencyRates()
        }
}