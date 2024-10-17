package com.example.bbank.domain.usecase.local

import com.example.bbank.domain.models.CurrencyRates

class GetLocalCurrencyRatesUseCase(
//    private val currencyRepository: CurrencyRepository
) {
    suspend operator fun invoke(): List<CurrencyRates> = emptyList()
//        currencyRepository.getLocalCurrencyRates()
}