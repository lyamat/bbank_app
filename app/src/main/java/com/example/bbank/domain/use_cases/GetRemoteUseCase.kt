package com.example.bbank.domain.use_cases

import com.example.bbank.data.remote.dto.toDepartment
import com.example.bbank.data.remote.dto.toNews
import com.example.bbank.domain.models.Department
import com.example.bbank.domain.models.News
import com.example.bbank.domain.repositories.RemoteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetRemoteUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    suspend fun getDepartmentsByCity(city: String): List<Department> =
        remoteRepository.getRemoteDepartmentsByCity(city).map {
            it.toDepartment()
        }

    suspend fun getNews(): List<News> =
        remoteRepository.getRemoteNews().map {
            it.toNews()
        }
}