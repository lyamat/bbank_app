package com.example.bbank.domain.use_cases.remote

import com.example.bbank.data.remote.dto.toDepartment
import com.example.bbank.domain.models.Department
import com.example.bbank.domain.repositories.RemoteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetRemoteDepartmentsByCityUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(city: String): List<Department> =
        remoteRepository.getRemoteDepartmentsByCity(city).map {
            it.toDepartment()
        }
}