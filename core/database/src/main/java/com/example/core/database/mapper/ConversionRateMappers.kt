package com.example.core.database.mapper

import com.example.core.database.entity.ConversionRateEntity
import com.example.core.domain.converter.ConversionRate
import com.example.core.domain.converter.Currency

fun ConversionRate.toConversionRateEntity(): ConversionRateEntity {
    return ConversionRateEntity(
        fromCurrency = fromCurrency.name,
        toCurrency = toCurrency.name,
        rateIn = rateIn,
        rateOut = rateOut
    )
}

fun ConversionRateEntity.toConversionRate(): ConversionRate {
    return ConversionRate(
        fromCurrency = Currency.valueOf(fromCurrency),
        toCurrency = Currency.valueOf(toCurrency),
        rateIn = rateIn,
        rateOut = rateOut
    )
}
