package com.example.bbank.presentation.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.converter.filterAvailableRates
import com.example.core.data.converter.getAvailableCurrencies
import com.example.core.domain.converter.ConversionRate
import com.example.core.domain.converter.ConverterRepository
import com.example.core.presentation.ui.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
                val availableConversionRates = conversionRates.filterAvailableRates()
                val availableCurrencies = availableConversionRates.getAvailableCurrencies()
                setConverterStateAvailableCurrencies(availableCurrencies)
                setConverterStateConversionRates(availableConversionRates)
                showSavedCurrencyValues()
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun showSavedCurrencyValues() {
        val currencyValues =
            viewModelScope.async { converterRepository.getCurrencyValues() }.await()
        setConverterStateCurrencyValues(currencyValues)
    }

    fun handleConverterEvent(event: ConverterEvent) {
        viewModelScope.launch {
            when (event) {
                is ConverterEvent.ConverterValueChanged -> {
                    // TODO: move to data layer
                    // TODO: feature:news,departments...
                    // TODO: base_utils to converter:core,presentation...
                    val updatedValues = _state.value.currencyValues.map { pair ->
                        if (pair.first == event.currencyCode) {
                            event.currencyCode to event.newValue
                        } else {
                            val conversionRate = _state.value.conversionRates.find {
                                it.fromCurrency.name == event.currencyCode && it.toCurrency.name == pair.first
                            }
                            val newValue = conversionRate?.let {
                                if (_state.value.conversionMode == ConversionMode.IN) {
                                    // TODO: add event to  ConverterEvent, in/out
                                    // TODO: make double values .%2s on ui 
                                    (event.newValue.toDouble() * it.rateIn).toString()
                                } else {
                                    (event.newValue.toDouble() * it.rateOut).toString()
                                }
                            } ?: pair.second
                            pair.first to newValue
                        }
                    }
                    setConverterStateCurrencyValues(updatedValues)
                    converterRepository.setCurrencyValues(updatedValues)
                }

                is ConverterEvent.ClearCurrencyValues -> {
                    val clearedCurrencyValues = _state.value.currencyValues.map { it.first to "0" }
                    setConverterStateCurrencyValues(clearedCurrencyValues)
                    converterRepository.setCurrencyValues(clearedCurrencyValues)
                }

                is ConverterEvent.UpdateCurrencyValues -> {
                    setConverterStateCurrencyValues(event.newsCurrencyValues)
                    converterRepository.setCurrencyValues(event.newsCurrencyValues)
                }
            }
        }
    }


    private fun setConverterStateCurrencyValues(currencyValues: List<Pair<CurrencyCode, CurrencyValue>>) =
        _state.update { it.copy(currencyValues = currencyValues) }

    private fun setConverterStateConversionRates(conversionRates: List<ConversionRate>) =
        _state.update { it.copy(conversionRates = conversionRates) }

    private fun setConverterStateAvailableCurrencies(availableCurrencies: List<CurrencyCode>) =
        _state.update { it.copy(availableCurrencies = availableCurrencies) }

    private fun setConverterStateConversionMode(conversionMode: ConversionMode) =
        _state.update { it.copy(conversionMode = conversionMode) }

    fun setConverterStateError(uiText: UiText?) =
        _state.update { it.copy(error = uiText) }
}