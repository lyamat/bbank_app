package com.example.bbank.presentation.converter

sealed class ConverterEvent {
    data class ConverterValueChanged(val currencyCode: String, val newValue: String) :
        ConverterEvent()

    data class UpdateCurrencyValues(val newsCurrencyValues: List<Pair<CurrencyCode, CurrencyValue>>) :
        ConverterEvent()

    data object ClearCurrencyValues : ConverterEvent()
}
