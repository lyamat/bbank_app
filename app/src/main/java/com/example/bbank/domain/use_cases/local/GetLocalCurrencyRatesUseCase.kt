package com.example.bbank.domain.use_cases.local

import com.example.bbank.data.local.currency_rates.toCurrencyRates
import com.example.bbank.domain.models.CurrencyRates
import com.example.bbank.domain.repositories.LocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetLocalCurrencyRatesUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(): List<CurrencyRates> =
        localRepository.getLocalCurrencyRates().map {
            it.toCurrencyRates()
        }
}