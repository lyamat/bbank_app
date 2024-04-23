package com.example.bbank.domain.use_cases

import com.example.bbank.data.local.currency_rates.toCurrencyRates
import com.example.bbank.data.local.departments.toDepartment
import com.example.bbank.data.local.news.toNews
import com.example.bbank.domain.models.CurrencyRates
import com.example.bbank.domain.models.Department
import com.example.bbank.domain.models.News
import com.example.bbank.domain.models.toCurrencyRatesEntity
import com.example.bbank.domain.models.toDepartmentEntity
import com.example.bbank.domain.models.toNewsEntity
import com.example.bbank.domain.repositories.LocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetLocalUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend fun getLocalNews(): List<News> =
        localRepository.getLocalNews().map {
            it.toNews()
        }

    suspend fun saveToLocalNews(news: List<News>) =
        news.map {
            localRepository.saveToLocalNews(it.toNewsEntity())
        }

    suspend fun deleteAllLocalNews() =
        localRepository.deleteAllLocalNews()

    suspend fun getLocalDepartmentsByCity(cityName: String): List<Department> =
        localRepository.getLocalDepartmentsByCity(cityName).map {
            it.toDepartment()
        }

    suspend fun saveToLocalDepartments(departments: List<Department>) =
        departments.map {
            localRepository.saveToLocalDepartments(it.toDepartmentEntity())
        }

    suspend fun deleteAllLocalDepartments() =
        localRepository.deleteAllLocalDepartments()

    suspend fun getLocalCurrencyRates(): List<CurrencyRates> =
        localRepository.getLocalCurrencyRates().map {
            it.toCurrencyRates()
        }

    suspend fun saveToLocalCurrencyRates(departments: List<Department>) =
        localRepository.saveToLocalCurrencyRates(departments[0].toCurrencyRatesEntity())

    suspend fun deleteAllCurrencyRates() =
        localRepository.deleteAllCurrencyRates()

    suspend fun getCurrentCity(): String =
        localRepository.getCurrentCity()

    suspend fun saveCurrentCity(cityName: String) =
        localRepository.saveCurrentCity(cityName)

    suspend fun getCurrencyValues(): List<Pair<String, String>> =
        localRepository.getCurrencyValues()

    suspend fun setCurrencyValues(currencyValues: List<Pair<String, String>>) =
        localRepository.setCurrencyValues(currencyValues)
}
