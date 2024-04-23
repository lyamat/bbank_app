package com.example.bbank.domain.repositories

import com.example.bbank.data.local.currency_rates.CurrencyRatesEntity
import com.example.bbank.data.local.departments.DepartmentEntity
import com.example.bbank.data.local.news.NewsEntity

internal interface LocalRepository {
    suspend fun getLocalNews(): List<NewsEntity>
    suspend fun getLocalNewsById(id: Long): NewsEntity
    suspend fun saveToLocalNews(newsEntity: NewsEntity)
    suspend fun deleteAllLocalNews()

    suspend fun getAllLocalDepartments(): List<DepartmentEntity>
    suspend fun getLocalDepartmentById(id: Long): DepartmentEntity
    suspend fun getLocalDepartmentsByCity(cityName: String): List<DepartmentEntity>
    suspend fun saveToLocalDepartments(departmentEntity: DepartmentEntity)
    suspend fun deleteAllLocalDepartments()

    suspend fun getLocalCurrencyRates(): List<CurrencyRatesEntity>
    suspend fun saveToLocalCurrencyRates(currencyRatesEntity: CurrencyRatesEntity)
    suspend fun deleteAllCurrencyRates()

    suspend fun getCurrentCity(): String
    suspend fun saveCurrentCity(cityName: String)

    suspend fun getCurrencyValues(): List<Pair<String, String>>
    suspend fun setCurrencyValues(currencyValues: List<Pair<String, String>>)
}