package com.example.bbank.domain.usecase.local

import com.example.bbank.domain.models.Department

class GetLocalDepartmentsByCityUseCase(
//    private val departmentRepository: DepartmentRepository
) {
    suspend operator fun invoke(cityName: String): List<Department> = emptyList()
//        departmentRepository.getLocalDepartmentsByCity(cityName)
}