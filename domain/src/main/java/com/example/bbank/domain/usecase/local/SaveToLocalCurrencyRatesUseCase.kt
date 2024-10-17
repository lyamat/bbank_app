package com.example.bbank.domain.usecase.local

import com.example.bbank.domain.models.Department

class SaveToLocalCurrencyRatesUseCase(
//    private val currencyRepository: CurrencyRepository
) {
    suspend operator fun invoke(department: Department) = Unit
//        currencyRepository.saveToLocalCurrencyRates(department)
}