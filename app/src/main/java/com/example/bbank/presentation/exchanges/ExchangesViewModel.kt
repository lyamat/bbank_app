package com.example.bbank.presentation.exchanges

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbank.domain.models.Exchanges
import com.example.bbank.domain.use_cases.GetRemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ExchangesViewModel @Inject constructor(
    private val getRemoteExchangesByCityUseCase: GetRemoteUseCase
) : ViewModel() {

//    init {
//        eventHolder(ExchangesEvent.Unspecified)
//    }

    private val _exchangesFlow: MutableStateFlow<ExchangesEvent> =
        MutableStateFlow(value = ExchangesEvent.Unspecified)

    internal fun exchangesFlow(): Flow<ExchangesEvent> = _exchangesFlow

    internal fun uploadRemoteExchanges() {
        viewModelScope.launch {
            try {
                eventHolder(ExchangesEvent.Loading)
                val exchanges = getRemoteExchangesByCityUseCase.getExchanges("Брест") // TODO: город - хардкод
                eventHolder(ExchangesEvent.Success(exchanges))
            } catch (e: Exception) {
                eventHolder(ExchangesEvent.Error(e.message.toString()))
//                Log.i("ExchangesViewModel", e.message.toString())
            }
        }
    }

    private fun eventHolder(event: ExchangesEvent) = viewModelScope.launch {
        _exchangesFlow.emit(event)
    }

    internal sealed class ExchangesEvent {
        data object Unspecified : ExchangesEvent()
        data class Success(val exchanges: List<Exchanges>) : ExchangesEvent()
        data class Error(val message: String) : ExchangesEvent()
        data object Loading : ExchangesEvent()
    }
}

