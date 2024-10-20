package com.example.core.domain.department

import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface DepartmentRepository {
    fun getDepartments(): Flow<List<Department>>
    fun getDepartmentById(id: String): Flow<Department>
    fun getDepartmentsByCity(cityName: String): Flow<List<Department>>
    suspend fun upsertDepartments(departments: List<Department>)
    suspend fun deleteAllDepartments()

    suspend fun fetchDepartments(): Result<List<Department>, DataError.Network>
}