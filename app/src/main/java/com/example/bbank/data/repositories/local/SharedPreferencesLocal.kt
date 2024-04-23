package com.example.bbank.data.repositories.local

internal interface SharedPreferencesLocal {
    suspend fun getCurrentCity(): String
    suspend fun saveCurrentCity(cityName: String)
    suspend fun getCurrencyValues(): List<Pair<String, String>>
    suspend fun setCurrencyValues(currencyValues: List<Pair<String, String>>)
}