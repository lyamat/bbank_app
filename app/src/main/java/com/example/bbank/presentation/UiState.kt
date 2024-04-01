package com.example.bbank.presentation

sealed class UiState<out T : Any> {
    data class Success<out T: Any>(val data: T) : UiState<T>()
    data class Error(val error: String) : UiState<Nothing>()
    object Loading : UiState<Nothing>()
}
