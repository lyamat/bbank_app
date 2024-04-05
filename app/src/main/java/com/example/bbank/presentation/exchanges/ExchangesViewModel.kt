package com.example.bbank.presentation.exchanges

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbank.domain.models.Exchanges
import com.example.bbank.domain.use_cases.GetRemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ExchangesViewModel @Inject constructor(
    private val getRemoteExchangesByCityUseCase: GetRemoteUseCase
) : ViewModel() {

    private val _exchangesFlow = MutableSharedFlow<ExchangesEvent>()
    internal fun exchangesFlow(): Flow<ExchangesEvent> = _exchangesFlow

    internal fun uploadRemoteExchanges() {
        viewModelScope.launch {
            try {
                eventHolder(ExchangesEvent.Loading)
                val exchanges =
                    getRemoteExchangesByCityUseCase.getExchanges("Брест") // TODO: change dynamically
                eventHolder(ExchangesEvent.Success(exchanges))
            } catch (e: Exception) {
                eventHolder(ExchangesEvent.Error(e.message.toString()))
            }
        }
    }

    internal fun updateSelectedCity(city: String) {
        viewModelScope.launch {
            try {
                eventHolder(ExchangesEvent.CityUpdate(city))
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
        data class Success(val exchanges: List<Exchanges>) : ExchangesEvent()
        data class Error(val message: String) : ExchangesEvent()
        data class CityUpdate(val city: String) : ExchangesEvent()
        data object Loading : ExchangesEvent()
    }
}

