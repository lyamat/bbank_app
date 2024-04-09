package com.example.bbank.data.repositories.local

import android.content.SharedPreferences
import javax.inject.Inject

internal class SharedPreferencesLocalImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferencesLocal {
    override suspend fun getCurrentCity(): String =
        sharedPreferences.getString("currentCity", "") ?: ""
}