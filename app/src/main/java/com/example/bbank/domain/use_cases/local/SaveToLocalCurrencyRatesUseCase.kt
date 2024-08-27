package com.example.bbank.domain.use_cases.local

import com.example.bbank.domain.models.Department
import com.example.bbank.domain.models.toCurrencyRatesEntity
import com.example.bbank.domain.repositories.CurrencyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SaveToLocalCurrencyRatesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {
    suspend operator fun invoke(departments: List<Department>) =
        currencyRepository.saveToLocalCurrencyRates(departments[0].toCurrencyRatesEntity())
}