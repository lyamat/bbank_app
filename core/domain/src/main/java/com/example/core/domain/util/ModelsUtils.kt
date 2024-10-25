package com.example.core.domain.util

import com.example.core.domain.converter.ConversionRate
import com.example.core.domain.department.Department

fun List<Department>.getUniqueCities(): List<String> {
    return this
        .map { it.name }
        .filter { it.isNotBlank() }
        .distinct()
}

fun List<ConversionRate>.getAvailableRates(): List<ConversionRate> {
    return this.filter { it.rateIn != 0.0 && it.rateOut != 0.0 }
}

fun List<ConversionRate>.getAvailableCurrencies(): List<String> {
    return this.flatMap { listOf(it.fromCurrency.name, it.toCurrency.name) }
        .distinct()
}