package com.example.bbank.domain.models

internal data class ConversionRate(
    val from: String,
    val to: String,
    val rate: Double
)

internal enum class CurrencyUnit(val unit: Int) {
    USD(1),
    EUR(1),
    RUB(100),
    GBP(1),
    CAD(1),
    PLN(10),
    SEK(10),
    CHF(1),
    JPY(100),
    CNY(10),
    CZK(100),
    NOK(10)
}