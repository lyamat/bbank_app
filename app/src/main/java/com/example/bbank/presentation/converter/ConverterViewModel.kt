package com.example.bbank.presentation.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbank.domain.models.Currency
import com.example.bbank.domain.use_cases.GetLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ConverterViewModel @Inject constructor(
    private val getLocalUseCase: GetLocalUseCase
) : ViewModel() {

    private val _currenciesFlow = MutableStateFlow<CurrenciesEvent>(CurrenciesEvent.Unspecified)
    internal fun currenciesFlow(): Flow<CurrenciesEvent> = _currenciesFlow

    internal fun updateCurrencyValue(currencyCode: String, newValue: Double) {
        viewModelScope.launch {
            try {
                eventHolder(CurrenciesEvent.Loading)

            } catch (e: Exception) {
                eventHolder(CurrenciesEvent.Error(e.message.toString()))
            }
        }
    }

    private fun eventHolder(event: CurrenciesEvent) {
        viewModelScope.launch {
            _currenciesFlow.emit(event)
        }
    }

//    internal fun updateCurrencyValue() {
//        val updatedCurrencies = currencies.value.map { currency ->
//            if (currency.currencyCode == currencyCode) {
//                currency.copy(currencyValue = newValue)
//            } else {
//                val conversionRate = getConversionRate(currencyCode, currency.currencyCode)
//                currency.copy(currencyValue = newValue * conversionRate)
//            }
//        }
//        currencies.value = updatedCurrencies
//    }

    private fun getConversionRate(fromCurrencyCode: String, toCurrencyCode: String): Double {
        return 0.2
    }


    internal sealed class CurrenciesEvent {
        data object Unspecified : CurrenciesEvent()
        data class CurrenciesSuccess(val currencies: List<Currency>) : CurrenciesEvent()
        data class Error(val message: String) : CurrenciesEvent()
        data object Loading : CurrenciesEvent()
    }

}