package com.example.core.domain.converter

data class ConversionRate(
    val fromCurrency: Currency,
    val toCurrency: Currency,
    val rateIn: Double,
    val rateOut: Double
)