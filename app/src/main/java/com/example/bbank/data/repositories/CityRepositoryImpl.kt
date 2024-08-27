package com.example.bbank.data.repositories

import com.example.bbank.data.repositories.local.SharedPreferencesLocal
import com.example.bbank.domain.repositories.CityRepository
import javax.inject.Inject

internal class CityRepositoryImpl @Inject constructor(
    private val sharedPreferencesLocal: SharedPreferencesLocal
) : CityRepository {
    override suspend fun getCurrentCity(): String =
        sharedPreferencesLocal.getCurrentCity()

    override suspend fun saveCurrentCity(cityName: String) =
        sharedPreferencesLocal.saveCurrentCity(cityName)
}