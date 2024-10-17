package com.example.bbank.domain.usecase.local

class GetCurrencyValuesUseCase(
//    private val currencyRepository: CurrencyRepository
) {
    suspend operator fun invoke(): List<Pair<String, String>> = emptyList()
//        currencyRepository.getLocalCurrencyValues()
}