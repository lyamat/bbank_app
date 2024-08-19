package com.example.bbank.presentation.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbank.domain.models.ConversionRate
import com.example.bbank.domain.models.getConversionRates
import com.example.bbank.domain.use_cases.local.GetCurrencyValuesUseCase
import com.example.bbank.domain.use_cases.local.GetLocalCurrencyRatesUseCase
import com.example.bbank.domain.use_cases.local.SetCurrencyValuesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ConverterViewModel @Inject constructor(
    private val getLocalCurrencyRatesUseCase: GetLocalCurrencyRatesUseCase,
    private val getCurrencyValuesUseCase: GetCurrencyValuesUseCase,
    private val setCurrencyValuesUseCase: SetCurrencyValuesUseCase
) : ViewModel() {

    private val _converterFlow = MutableSharedFlow<ConverterEvent>()
    internal fun converterFlow(): SharedFlow<ConverterEvent> = _converterFlow

    internal fun getDataForConverterAdapter() {
        viewModelScope.launch {
            try {
                val currencyRates = getLocalCurrencyRatesUseCase()
                if (currencyRates.isNotEmpty()) {
                    val conversionRates = currencyRates[0].getConversionRates()
                    val currencyValues = getCurrencyValuesUseCase()
                    eventHolder(ConverterEvent.AdapterDataSuccess(currencyValues, conversionRates))
                }
            } catch (e: Exception) {
                eventHolder(ConverterEvent.Error(e.message.toString()))
            }
        }
    }

    internal fun setCurrencyValues(currencyValues: List<Pair<String, String>>) {
        viewModelScope.launch {
            try {
                setCurrencyValuesUseCase(currencyValues)
            } catch (e: Exception) {
                eventHolder(ConverterEvent.Error(e.message.toString()))
            }
        }
    }

    internal fun getCurrencyValues() {
        viewModelScope.launch {
            try {
                eventHolder(ConverterEvent.Loading)
                val currencyValues = getCurrencyValuesUseCase()
                eventHolder(ConverterEvent.CurrencyValuesSuccess(currencyValues))
            } catch (e: Exception) {
                eventHolder(ConverterEvent.Error(e.message.toString()))
            }
        }
    }

    private fun eventHolder(event: ConverterEvent) =
        viewModelScope.launch {
            _converterFlow.emit(event)
        }

    internal sealed class ConverterEvent {
        data object Unspecified : ConverterEvent()
        data class AdapterDataSuccess(
            val currencyValues: List<Pair<String, String>>,
            val conversionRates: List<ConversionRate>
        ) : ConverterEvent()

        data class CurrencyValuesSuccess(val currencyValues: List<Pair<String, String>>) :
            ConverterEvent()

        data class Error(val message: String) : ConverterEvent()
        data object Loading : ConverterEvent()
    }
}