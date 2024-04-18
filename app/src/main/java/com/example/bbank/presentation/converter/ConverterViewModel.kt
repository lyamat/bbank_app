package com.example.bbank.presentation.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

//    internal fun updateCurrencyValue(
//        currencyValues: List<Pair<String, String>>,
//        currencyCode: String,
//        newValue: String
//    ) {
//        viewModelScope.launch {
//            try {
//                eventHolder(CurrenciesEvent.Loading)
//                val updatedCurrencies = getNewCurrencyValues(currencyValues, currencyCode, newValue)
//                eventHolder(CurrenciesEvent.CurrenciesSuccess(updatedCurrencies))
//            } catch (e: Exception) {
//                eventHolder(CurrenciesEvent.Error(e.message.toString()))
//            }
//        }
//    }

//    private fun getNewCurrencyValues(
//        currencyValues: List<Pair<String, String>>,
//        currencyCode: String,
//        newValue: String
//    ): List<Pair<String, String>> {
//        return currencyValues.map { pair ->
//            if (pair.first == currencyCode) {
//                pair.copy(second = newValue)
//            } else {
//                val conversionRate = getConversionRate(currencyCode, pair.first)
//                val newPairValue = String.format(
//                    Locale.US,
//                    "%.3f",
//                    newValue.replace(',', '.').toDouble() * conversionRate
//                )
//                pair.copy(second = newPairValue)
//            }
//        }
//    }
//
//    private fun getConversionRate(from: String, to: String): Double {
//        return when {
//            from == "usd" && to == "rub" -> 93.0
//            from == "usd" && to == "eur" -> 0.9319
//            from == "rub" && to == "usd" -> 0.0107
//            from == "rub" && to == "eur" -> 0.0099
//            from == "eur" && to == "usd" -> 1.0731
//            from == "eur" && to == "rub" -> 100.6735
//            else -> 1.0
//        }
//    }

    private fun eventHolder(event: CurrenciesEvent) {
        viewModelScope.launch {
            _currenciesFlow.emit(event)
        }
    }

    internal sealed class CurrenciesEvent {
        data object Unspecified : CurrenciesEvent()
        data class CurrenciesSuccess(val currencyValues: List<Pair<String, String>>) :
            CurrenciesEvent()

        data class Error(val message: String) : CurrenciesEvent()
        data object Loading : CurrenciesEvent()
    }
}