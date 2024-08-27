package com.example.bbank.domain.use_cases.local

import com.example.bbank.domain.repositories.DepartmentRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DeleteAllLocalDepartmentsUseCase @Inject constructor(
    private val departmentRepository: DepartmentRepository
) {
    suspend operator fun invoke() =
        departmentRepository.deleteAllLocalDepartments()
}