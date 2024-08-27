package com.example.bbank.domain.use_cases.local

import com.example.bbank.domain.repositories.CurrencyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SetCurrencyValuesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {
    suspend operator fun invoke(currencyValues: List<Pair<String, String>>) =
        currencyRepository.setCurrencyValues(currencyValues)
}