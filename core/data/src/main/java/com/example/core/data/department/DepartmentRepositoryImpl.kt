package com.example.core.data.department

import com.example.core.domain.department.Department
import com.example.core.domain.department.DepartmentRepository
import com.example.core.domain.department.LocalDepartmentDataSource
import com.example.core.domain.department.RemoteDepartmentDataSource
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DepartmentRepositoryImpl @Inject constructor(
    private val localDepartmentDataSource: LocalDepartmentDataSource,
    private val remoteDepartmentDataSource: RemoteDepartmentDataSource
) : DepartmentRepository {
    override fun getDepartments(): Flow<List<Department>> {
        return localDepartmentDataSource.getDepartments()
    }

    override fun getDepartmentById(id: String): Flow<Department> {
        return localDepartmentDataSource.getDepartmentById(id)
    }

    override fun getDepartmentsByCity(cityName: String): Flow<List<Department>> {
        return localDepartmentDataSource.getDepartmentsByCity(cityName)
    }

    override suspend fun upsertDepartments(departments: List<Department>) {
        localDepartmentDataSource.upsertDepartments(departments)
    }

    override suspend fun deleteAllDepartments() {
        localDepartmentDataSource.deleteAllDepartments()
    }

    override suspend fun fetchDepartments(): Result<List<Department>, DataError.Network> {
        return remoteDepartmentDataSource.getDepartments()
    }
}
