package com.example.bbank.presentation.converter

import com.example.core.domain.converter.ConversionRate
import com.example.core.presentation.ui.UiText

typealias CurrencyCode = String
typealias CurrencyValue = String

data class ConverterState(
    val currencyValues: List<Pair<CurrencyCode, CurrencyValue>> = emptyList(),
    val conversionRates: List<ConversionRate> = emptyList(),
    val conversionMode: ConversionMode = ConversionMode.IN,
    val availableCurrencies: List<CurrencyCode> = emptyList(),
    val error: UiText? = null
)

enum class ConversionMode {
    IN, OUT
}