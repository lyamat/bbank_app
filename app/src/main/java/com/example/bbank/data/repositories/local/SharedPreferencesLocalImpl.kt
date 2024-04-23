package com.example.bbank.data.repositories.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

internal class SharedPreferencesLocalImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferencesLocal {
    override suspend fun getCurrentCity(): String =
        sharedPreferences.getString("currentCity", "") ?: ""

    override suspend fun saveCurrentCity(cityName: String) {
        sharedPreferences.edit().putString("currentCity", cityName).apply()
    }

    override suspend fun getCurrencyValues(): List<Pair<String, String>> {
        val gson = Gson()
        val currencyValues = sharedPreferences.getString("currencyValues", "") ?: ""
        return if (currencyValues.isEmpty()) {
            listOf(Pair("byn", ""), Pair("usd", ""))
        } else {
            val type = object : TypeToken<List<Pair<String, String>>>() {}.type
            gson.fromJson(currencyValues, type)
        }
    }

    override suspend fun setCurrencyValues(currencyValues: List<Pair<String, String>>) {
        val gson = Gson()
        val currencyValuesJson = gson.toJson(currencyValues)
        with(sharedPreferences.edit()) {
            putString("currencyValues", currencyValuesJson)
            apply()
        }
    }
}