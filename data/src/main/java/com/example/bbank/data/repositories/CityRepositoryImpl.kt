//package com.example.bbank.data.repositories
//
//import com.example.bbank.data.repositories.local.SharedPreferencesLocal
//import com.example.bbank.domain.repository.CityRepository
//import javax.inject.Inject
//
//class CityRepositoryImpl @Inject constructor(
//    private val sharedPreferencesLocal: SharedPreferencesLocal
//) : CityRepository {
//    override suspend fun getCurrentCity(): String =
//        sharedPreferencesLocal.getCurrentCity()
//
//    override suspend fun saveCurrentCity(cityName: String) =
//        sharedPreferencesLocal.saveCurrentCity(cityName)
//}