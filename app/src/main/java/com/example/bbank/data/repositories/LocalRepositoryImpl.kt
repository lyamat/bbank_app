package com.example.bbank.data.repositories

import com.example.bbank.data.local.currency_rates.CurrencyRatesEntity
import com.example.bbank.data.local.departments.DepartmentEntity
import com.example.bbank.data.local.news.NewsEntity
import com.example.bbank.data.repositories.local.CurrencyRatesLocal
import com.example.bbank.data.repositories.local.DepartmentLocal
import com.example.bbank.data.repositories.local.NewsLocal
import com.example.bbank.data.repositories.local.SharedPreferencesLocal
import com.example.bbank.domain.repositories.LocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class LocalRepositoryImpl @Inject constructor(
    private val newsLocal: NewsLocal,
    private val departmentLocal: DepartmentLocal,
    private val currencyRatesLocal: CurrencyRatesLocal,
    private val sharedPreferencesLocal: SharedPreferencesLocal
) : LocalRepository {
    override suspend fun getLocalNews(): List<NewsEntity> =
        newsLocal.getLocalNews() ?: listOf(NewsEntity.empty())

    override suspend fun getLocalNewsById(id: Long): NewsEntity =
        newsLocal.getLocalNewsById(id = id) ?: NewsEntity.empty()

    override suspend fun saveToLocalNews(newsEntity: NewsEntity) =
        newsLocal.saveToLocalNews(newsEntity)

    override suspend fun deleteAllLocalNews() {
        newsLocal.deleteAllLocalNews()
    }

    override suspend fun getAllLocalDepartments(): List<DepartmentEntity> =
        departmentLocal.getAllLocalDepartments() ?: listOf(DepartmentEntity.empty())

    override suspend fun getLocalDepartmentById(id: Long): DepartmentEntity =
        departmentLocal.getLocalDepartmentById(id) ?: DepartmentEntity.empty()

    override suspend fun getLocalDepartmentsByCity(cityName: String): List<DepartmentEntity> =
        if (cityName.isBlank()) {
            departmentLocal.getAllLocalDepartments() ?: listOf(DepartmentEntity.empty())
        } else {
            departmentLocal.getLocalDepartmentsByCity(cityName)
                ?: listOf(DepartmentEntity.empty())
        }

    override suspend fun saveToLocalDepartments(departmentEntity: DepartmentEntity) =
        departmentLocal.saveToLocalDepartments(departmentEntity)

    override suspend fun deleteAllLocalDepartments() =
        departmentLocal.deleteAllLocalDepartments()

    override suspend fun getLocalCurrencyRates(): List<CurrencyRatesEntity> =
        currencyRatesLocal.getLocalCurrencyRates() ?: listOf(CurrencyRatesEntity.empty())

    override suspend fun saveToLocalCurrencyRates(currencyRatesEntity: CurrencyRatesEntity) =
        currencyRatesLocal.saveToLocalCurrencyRates(currencyRatesEntity)

    override suspend fun deleteAllCurrencyRates() =
        currencyRatesLocal.deleteAllLocalCurrencyRates()

    override suspend fun getCurrentCity(): String =
        sharedPreferencesLocal.getCurrentCity()

    override suspend fun saveCurrentCity(cityName: String) {
        sharedPreferencesLocal.saveCurrentCity(cityName)
    }

    override suspend fun getCurrencyValues(): List<Pair<String, String>> =
        sharedPreferencesLocal.getCurrencyValues()

    override suspend fun setCurrencyValues(currencyValues: List<Pair<String, String>>) {
        sharedPreferencesLocal.setCurrencyValues(currencyValues)
    }
}