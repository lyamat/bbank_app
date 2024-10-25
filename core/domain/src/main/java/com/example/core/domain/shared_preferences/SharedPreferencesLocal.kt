package com.example.core.domain.shared_preferences

interface SharedPreferencesLocal {
    suspend fun getCurrentCity(): String
    suspend fun setCurrentCity(cityName: String)
    suspend fun getCurrencyValues(): List<Pair<String, String>>
    suspend fun setCurrencyValues(currencyValues: List<Pair<String, String>>)
}