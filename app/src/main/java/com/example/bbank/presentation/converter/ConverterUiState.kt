package com.example.bbank.presentation.converter

import com.example.bbank.domain.models.ConversionRate
import com.example.bbank.presentation.utils.UiText

internal data class ConverterUiState(
    val currencyValues: List<Pair<String, String>> = emptyList(),
    val conversionRates: List<ConversionRate> = emptyList(),
    val isCurrencyAdding: Boolean = false,
    val isLoading: Boolean = false,
    val error: UiText? = null
)