package com.example.bbank.domain.use_cases.local

import com.example.bbank.data.local.departments.toDepartment
import com.example.bbank.domain.models.Department
import com.example.bbank.domain.repositories.LocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetLocalDepartmentsByCityUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(cityName: String): List<Department> =
        localRepository.getLocalDepartmentsByCity(cityName).map {
            it.toDepartment()
        }
}