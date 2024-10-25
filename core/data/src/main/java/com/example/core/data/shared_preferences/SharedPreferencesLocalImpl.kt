package com.example.core.data.shared_preferences

import android.content.SharedPreferences
import com.example.core.domain.shared_preferences.SharedPreferencesLocal
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class SharedPreferencesLocalImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferencesLocal {
    override suspend fun getCurrentCity(): String =
        sharedPreferences.getString("currentCity", "") ?: ""

    override suspend fun setCurrentCity(cityName: String) {
        sharedPreferences.edit().putString("currentCity", cityName).apply()
    }

    override suspend fun getCurrencyValues(): List<Pair<String, String>> {
        val gson = GsonBuilder().create()
        val currencyValuesJson = sharedPreferences.getString("currencyValues", "") ?: ""

        return if (currencyValuesJson.isEmpty() || currencyValuesJson == "[]") {
            listOf(Pair("BYN", ""), Pair("USD", ""))
        } else {
            val typeToken = object : TypeToken<List<Pair<String, String>>>() {}.type
            gson.fromJson(currencyValuesJson, typeToken)
        }
    }

    override suspend fun setCurrencyValues(currencyValues: List<Pair<String, String>>) {
        val gson = GsonBuilder().create()
        val currencyValuesJson = gson.toJson(currencyValues)

        with(sharedPreferences.edit()) {
            putString("currencyValues", currencyValuesJson)
            apply()
        }
    }
}