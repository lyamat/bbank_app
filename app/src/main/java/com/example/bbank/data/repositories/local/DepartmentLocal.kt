package com.example.bbank.data.repositories.local

import com.example.bbank.data.local.departments.DepartmentEntity

internal interface DepartmentLocal {
    suspend fun getAllLocalDepartments(): List<DepartmentEntity>?
    suspend fun getLocalDepartmentById(id: Long): DepartmentEntity?
    suspend fun getLocalDepartmentsByCity(cityName: String): List<DepartmentEntity>?
    suspend fun saveToLocalDepartments(departmentEntity: DepartmentEntity)
    suspend fun deleteAllLocalDepartments()
}