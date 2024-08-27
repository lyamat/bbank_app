package com.example.bbank.domain.repositories

internal interface CityRepository {
    suspend fun getCurrentCity(): String
    suspend fun saveCurrentCity(cityName: String)
}