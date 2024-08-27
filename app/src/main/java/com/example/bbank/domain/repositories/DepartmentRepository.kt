package com.example.bbank.domain.repositories

import com.example.bbank.data.local.departments.DepartmentEntity
import com.example.bbank.data.remote.dto.DepartmentResponseDto
import retrofit2.Response

internal interface DepartmentRepository {
    suspend fun getAllLocalDepartments(): List<DepartmentEntity>
    suspend fun getLocalDepartmentById(id: Long): DepartmentEntity
    suspend fun getLocalDepartmentsByCity(cityName: String): List<DepartmentEntity>
    suspend fun saveToLocalDepartments(departmentEntity: DepartmentEntity)
    suspend fun deleteAllLocalDepartments()

    suspend fun getRemoteDepartmentsByCity(city: String): Response<List<DepartmentResponseDto>>
}
