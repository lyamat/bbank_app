package com.example.bbank.presentation.converter

sealed class ConverterEvent {
    data class ValuesChanged(val currencyCode: String, val newValue: String) : ConverterEvent()
    data object ClearAllValues : ConverterEvent()
}
