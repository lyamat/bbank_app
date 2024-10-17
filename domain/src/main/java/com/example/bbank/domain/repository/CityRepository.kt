package com.example.bbank.domain.repository

interface CityRepository {
    suspend fun getCurrentCity(): String
    suspend fun saveCurrentCity(cityName: String)
}