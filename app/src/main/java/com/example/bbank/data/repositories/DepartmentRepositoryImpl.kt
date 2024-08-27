package com.example.bbank.data.repositories

import com.example.bbank.data.local.departments.DepartmentEntity
import com.example.bbank.data.remote.dto.DepartmentResponseDto
import com.example.bbank.data.repositories.local.DepartmentLocal
import com.example.bbank.data.repositories.remote.DepartmentRemote
import com.example.bbank.domain.networking.orNullResponseError
import com.example.bbank.domain.repositories.DepartmentRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DepartmentRepositoryImpl @Inject constructor(
    private val departmentLocal: DepartmentLocal,
    private val departmentRemote: DepartmentRemote
) : DepartmentRepository {
    override suspend fun getAllLocalDepartments(): List<DepartmentEntity> =
        departmentLocal.getAllLocalDepartments() ?: listOf(DepartmentEntity.empty())

    override suspend fun getLocalDepartmentById(id: Long): DepartmentEntity =
        departmentLocal.getLocalDepartmentById(id) ?: DepartmentEntity.empty()

    override suspend fun getLocalDepartmentsByCity(cityName: String): List<DepartmentEntity> =
        if (cityName.isBlank()) {
            departmentLocal.getAllLocalDepartments() ?: listOf(DepartmentEntity.empty())
        } else {
            departmentLocal.getLocalDepartmentsByCity(cityName)
                ?: listOf(DepartmentEntity.empty())
        }

    override suspend fun saveToLocalDepartments(departmentEntity: DepartmentEntity) =
        departmentLocal.saveToLocalDepartments(departmentEntity)

    override suspend fun deleteAllLocalDepartments() =
        departmentLocal.deleteAllLocalDepartments()

    override suspend fun getRemoteDepartmentsByCity(city: String): Response<List<DepartmentResponseDto>> =
        departmentRemote.getRemoteDepartmentsByCity(city).orNullResponseError()
}
