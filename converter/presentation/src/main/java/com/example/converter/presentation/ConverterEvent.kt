package com.example.converter.presentation

sealed class ConverterEvent {
    data class CurrencyValueChanged(val currencyCode: String, val newValue: String) :
        ConverterEvent()

    data class UpdateCurrenciesInConverter(val newsCurrencyValues: List<Pair<CurrencyCode, CurrencyValue>>) :
        ConverterEvent()

    data object ClearCurrencyValues : ConverterEvent()
}
