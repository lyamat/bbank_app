package com.example.bbank.presentation.exchanges

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbank.domain.models.Exchanges
import com.example.bbank.domain.use_cases.GetLocalUseCase
import com.example.bbank.domain.use_cases.GetRemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ExchangesViewModel @Inject constructor(
    private val getRemoteUseCase: GetRemoteUseCase,
    private val getLocalUseCase: GetLocalUseCase
) : ViewModel() {

    private val _exchangesFlow = MutableSharedFlow<ExchangesEvent>()
    internal fun exchangesFlow(): Flow<ExchangesEvent> = _exchangesFlow

    internal fun getRemoteExchangesByCity(city: String) {
        viewModelScope.launch {
            try {
                eventHolder(ExchangesEvent.Loading)
                val exchanges = getRemoteUseCase.getExchangesByCity(city)
                getLocalUseCase.deleteAllLocalExchanges()
                getLocalUseCase.saveToLocalExchanges(exchanges)
                eventHolder(ExchangesEvent.ExchangesSuccess(exchanges))
            } catch (e: Exception) {
                eventHolder(ExchangesEvent.Error(e.message.toString()))
            }
        }
    }

    internal fun getLocalExchangesByCity() {
        viewModelScope.launch {
            try {
                eventHolder(ExchangesEvent.Loading)
                val cityName = getLocalUseCase.getCurrentCity()
                val exchanges = getLocalUseCase.getLocalExchangesByCity(cityName)
                eventHolder(ExchangesEvent.ExchangesSuccess(exchanges))
            } catch (e: Exception) {
                eventHolder(ExchangesEvent.Error(e.message.toString()))
            }
        }
    }

    internal fun getCurrentCity() {
        viewModelScope.launch {
            try {
                eventHolder(ExchangesEvent.Loading)
                val cityName = getLocalUseCase.getCurrentCity()
                eventHolder(ExchangesEvent.CitySuccess(cityName))
            } catch (e: Exception) {
                eventHolder(ExchangesEvent.Error(e.message.toString()))
            }
        }
    }

    private fun eventHolder(event: ExchangesEvent) {
        viewModelScope.launch {
            _exchangesFlow.emit(event)
        }
    }


    internal sealed class ExchangesEvent {
        data object Unspecified : ExchangesEvent()
        data class ExchangesSuccess(val exchanges: List<Exchanges>) : ExchangesEvent()
        data class Error(val message: String) : ExchangesEvent()
        data class CitySuccess(val cityName: String) : ExchangesEvent()
        data object Loading : ExchangesEvent()
    }
}

