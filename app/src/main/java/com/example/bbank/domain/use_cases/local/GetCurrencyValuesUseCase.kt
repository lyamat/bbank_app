package com.example.bbank.domain.use_cases.local

import com.example.bbank.domain.repositories.CurrencyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetCurrencyValuesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {
    suspend operator fun invoke(): List<Pair<String, String>> =
        currencyRepository.getCurrencyValues()
}