package com.example.bbank.domain.usecase.local

import com.example.bbank.domain.models.Department

class SaveToLocalDepartmentsUseCase(
//    private val departmentRepository: DepartmentRepository
) {
    suspend operator fun invoke(departments: List<Department>) = Unit
//        departments.forEach {
//            departmentRepository.saveToLocalDepartments(it)
//        }
}