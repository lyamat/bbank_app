package com.example.bbank.presentation.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbank.R
import com.example.core.domain.converter.ConverterRepository
import com.example.core.presentation.ui.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val converterRepository: ConverterRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ConverterState())
    val state: StateFlow<ConverterState> get() = _state

    init {
        converterRepository.getConversionRates().onEach { conversionRates ->
            if (conversionRates.isNotEmpty()) {
                _state.update { ConverterState(conversionRates = conversionRates) }
                showSavedCurrencyValues()
            } else {
                _state.update { ConverterState(error = UiText.StringResource(R.string.empty_currency_rates)) }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun showSavedCurrencyValues() {
        val currencyValues =
            viewModelScope.async { converterRepository.getCurrencyValues() }.await()
        _state.update { it.copy(currencyValues = currencyValues) }
    }

    fun handleConverterEvent(event: ConverterEvent) {
        when (event) {
            is ConverterEvent.ValuesChanged -> {
                // TODO: move to data layer
                val updatedValues = _state.value.currencyValues.map { pair ->
                    if (pair.first == event.currencyCode) {
                        event.currencyCode to event.newValue
                    } else {
                        val conversionRate = _state.value.conversionRates.find {
                            it.fromCurrency.name == event.currencyCode && it.toCurrency.name == pair.first
                        }
                        val newValue = conversionRate?.let {
                            if (_state.value.conversionMode == ConversionMode.IN) {
                                (event.newValue.toDouble() * it.rateIn).toString()
                            } else {
                                (event.newValue.toDouble() * it.rateOut).toString()
                            }
                        } ?: pair.second
                        pair.first to newValue
                    }
                }
                _state.update { it.copy(currencyValues = updatedValues) }
            }

            is ConverterEvent.ClearAllValues -> {
                val clearedCurrencyValues = _state.value.currencyValues.map { it.first to "0" }
                _state.update { it.copy(currencyValues = clearedCurrencyValues) }
            }
        }
    }

    private fun updateStateError(uiText: UiText) = _state.update { it.copy(error = uiText) }
}