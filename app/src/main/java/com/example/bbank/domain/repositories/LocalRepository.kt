package com.example.bbank.domain.repositories

import com.example.bbank.data.local.currency_rates.CurrencyRatesEntity
import com.example.bbank.data.local.departments.DepartmentEntity
import com.example.bbank.data.local.news.NewsEntity

internal interface LocalRepository {
    suspend fun getLocalNews(): List<NewsEntity>
    suspend fun getLocalNewsById(id: Long): NewsEntity
    suspend fun saveToLocalNews(newsEntity: NewsEntity)
    suspend fun deleteAllLocalNews(): Unit

    suspend fun getAllLocalDepartments(): List<DepartmentEntity>
    suspend fun getLocalDepartmentById(id: Long): DepartmentEntity
    suspend fun getLocalDepartmentsByCity(cityName: String): List<DepartmentEntity>
    suspend fun saveToLocalDepartments(departmentEntity: DepartmentEntity)
    suspend fun deleteAllLocalDepartments(): Unit

    suspend fun getLocalCurrencyRates(): List<CurrencyRatesEntity>
    suspend fun saveToLocalCurrencyRates(currencyRatesEntity: CurrencyRatesEntity)
    suspend fun deleteAllCurrencyRates(): Unit

    suspend fun getCurrentCity(): String
}