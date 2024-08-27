package com.example.bbank.domain.use_cases.local

import com.example.bbank.data.local.departments.toDepartment
import com.example.bbank.domain.models.Department
import com.example.bbank.domain.repositories.DepartmentRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetLocalDepartmentsByCityUseCase @Inject constructor(
    private val departmentRepository: DepartmentRepository
) {
    suspend operator fun invoke(cityName: String): List<Department> =
        departmentRepository.getLocalDepartmentsByCity(cityName).map {
            it.toDepartment()
        }
}