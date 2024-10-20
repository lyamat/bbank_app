package com.example.core.domain.shared_preferences

interface SharedPreferencesLocal {
    suspend fun getCurrentCity(): String
    suspend fun saveCurrentCity(cityName: String)
    suspend fun getCurrencyValues(): List<Pair<String, String>>
    suspend fun setCurrencyValues(currencyValues: List<Pair<String, String>>)
}