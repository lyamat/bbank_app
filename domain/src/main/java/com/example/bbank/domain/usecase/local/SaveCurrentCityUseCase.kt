package com.example.bbank.domain.usecase.local

class SaveCurrentCityUseCase(
//    private val cityRepository: CityRepository
) {
    suspend operator fun invoke(cityName: String) = Unit
//        cityRepository.saveCurrentCity(cityName)
}