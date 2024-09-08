package com.example.bbank.domain.use_cases.remote

import com.example.bbank.domain.models.Department
import com.example.bbank.domain.repositories.DepartmentRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetRemoteDepartmentsByCityUseCase @Inject constructor(
    private val departmentRepository: DepartmentRepository
) : BaseRemoteUseCase<Department, Department>(
    repositoryCall = { departmentRepository.getRemoteDepartmentsByCity("") },
    mapper = { it }
)