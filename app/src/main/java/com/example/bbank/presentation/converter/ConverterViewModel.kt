package com.example.bbank.presentation.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbank.domain.models.ConversionRate
import com.example.bbank.domain.models.getConversionRates
import com.example.bbank.domain.use_cases.GetLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ConverterViewModel @Inject constructor(
    private val getLocalUseCase: GetLocalUseCase
) : ViewModel() {

    private val _converterFlow = MutableSharedFlow<ConverterEvent>()
    internal fun converterFlow(): SharedFlow<ConverterEvent> = _converterFlow

    internal fun setCurrencyValues(currencyValues: List<Pair<String, String>>) {
        viewModelScope.launch {
            try {
                eventHolder(ConverterEvent.Loading)
                getLocalUseCase.setCurrencyValues(currencyValues)
//                eventHolder(ConverterEvent.Unspecified)
            } catch (e: Exception) {
                eventHolder(ConverterEvent.Error(e.message.toString()))
            }
        }
    }

    internal fun getCurrencyValues() {
        viewModelScope.launch {
            try {
                eventHolder(ConverterEvent.Loading)
                val currencyValues = getLocalUseCase.getCurrencyValues()
                eventHolder(ConverterEvent.CurrencyValuesSuccess(currencyValues))
            } catch (e: Exception) {
                eventHolder(ConverterEvent.Error(e.message.toString()))
            }
        }
    }

    internal fun getStartDataForConverterAdapter() {
        viewModelScope.launch {
            try {
                eventHolder(ConverterEvent.Loading)
                val currencyRates = getLocalUseCase.getLocalCurrencyRates()
                val conversionRates = currencyRates[0].getConversionRates()
                val currencyValues = getLocalUseCase.getCurrencyValues()
                eventHolder(ConverterEvent.AdapterDataSuccess(currencyValues, conversionRates))
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