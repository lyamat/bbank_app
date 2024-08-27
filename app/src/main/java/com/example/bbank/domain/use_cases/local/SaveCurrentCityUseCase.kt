package com.example.bbank.domain.use_cases.local

import com.example.bbank.domain.repositories.CityRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SaveCurrentCityUseCase @Inject constructor(
    private val cityRepository: CityRepository
) {
    suspend operator fun invoke(cityName: String) =
        cityRepository.saveCurrentCity(cityName)
}