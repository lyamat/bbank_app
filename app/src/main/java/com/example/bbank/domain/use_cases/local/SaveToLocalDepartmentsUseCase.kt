package com.example.bbank.domain.use_cases.local

import com.example.bbank.domain.models.Department
import com.example.bbank.domain.models.toDepartmentEntity
import com.example.bbank.domain.repositories.LocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SaveToLocalDepartmentsUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(departments: List<Department>) =
        departments.forEach {
            localRepository.saveToLocalDepartments(it.toDepartmentEntity())
        }
}