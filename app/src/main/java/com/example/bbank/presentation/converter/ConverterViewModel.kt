package com.example.bbank.presentation.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbank.R
import com.example.bbank.domain.models.getConversionRates
import com.example.bbank.domain.use_cases.local.GetCurrencyValuesUseCase
import com.example.bbank.domain.use_cases.local.GetLocalCurrencyRatesUseCase
import com.example.bbank.domain.use_cases.local.SetCurrencyValuesUseCase
import com.example.bbank.presentation.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ConverterViewModel @Inject constructor(
    private val getLocalCurrencyRatesUseCase: GetLocalCurrencyRatesUseCase,
    private val getCurrencyValuesUseCase: GetCurrencyValuesUseCase,
    private val setCurrencyValuesUseCase: SetCurrencyValuesUseCase
) : ViewModel() {

    init {
        getDataForConverterAdapter()
    }

    private val _converterUiState = MutableStateFlow(ConverterUiState())
    val converterUiState: StateFlow<ConverterUiState> get() = _converterUiState

    internal fun getDataForConverterAdapter() {
        viewModelScope.launch {
            try {
                val currencyRates = getLocalCurrencyRatesUseCase()
                if (currencyRates.isNotEmpty()) {
                    val conversionRates = currencyRates[0].getConversionRates()
                    val currencyValues = getCurrencyValuesUseCase()
                    _converterUiState.update {
                        ConverterUiState(
                            currencyValues = currencyValues,
                            conversionRates = conversionRates
                        )
                    }
                } else updateConverterStateWithError(UiText.StringResource(R.string.empty_currency_rates))
            } catch (e: Exception) {
                updateConverterStateWithError(e)
            }
        }
    }

    internal fun saveCurrencyValues(currencyValues: List<Pair<String, String>>) {
        viewModelScope.launch {
            try {
                setCurrencyValuesUseCase(currencyValues)
                _converterUiState.update { it.copy(isCurrencyAdding = false) }
            } catch (e: Exception) {
                updateConverterStateWithError(e)
            }
        }
    }

    internal fun startAddingCurrencyInConverter() {
        _converterUiState.update { it.copy(isCurrencyAdding = true) }
    }

    internal fun endAddingCurrencyInConverter() {
        _converterUiState.update { it.copy(isCurrencyAdding = false) }
    }

    private fun updateConverterStateWithError(uiText: UiText) =
        _converterUiState.update {
            it.copy(
                error = uiText,
                isLoading = false
            )
        }

    private fun updateConverterStateWithError(e: Exception) =
        _converterUiState.update {
            it.copy(
                error = e.message?.let { message -> UiText.DynamicString(message) },
                isLoading = false
            )
        }
}