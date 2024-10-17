package com.example.bbank.domain.usecase.local

class SetCurrencyValuesUseCase(
//    private val currencyRepository: CurrencyRepository
) {
    suspend operator fun invoke(currencyValues: List<Pair<String, String>>) = Unit
//        currencyRepository.setLocalCurrencyValues(currencyValues)
}