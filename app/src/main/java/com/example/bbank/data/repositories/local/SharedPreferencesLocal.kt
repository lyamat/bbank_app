package com.example.bbank.data.repositories.local

internal interface SharedPreferencesLocal {
    suspend fun getCurrentCity(): String
}