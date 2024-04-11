package com.example.bbank.domain.use_cases

import com.example.bbank.data.local.departments.toDepartment
import com.example.bbank.data.local.news.toNews
import com.example.bbank.domain.models.Department
import com.example.bbank.domain.models.News
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


    suspend fun getCurrentCity() =
        localRepository.getCurrentCity()
}
