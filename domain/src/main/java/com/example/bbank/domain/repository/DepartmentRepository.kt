package com.example.bbank.domain.repository

import com.example.bbank.domain.models.Department

//import retrofit2.Response

interface DepartmentRepository {
    suspend fun getAllLocalDepartments(): List<Department>
    suspend fun getLocalDepartmentById(id: Long): Department
    suspend fun getLocalDepartmentsByCity(cityName: String): List<Department>
    suspend fun saveToLocalDepartments(department: Department)
    suspend fun deleteAllLocalDepartments()

//    suspend fun getRemoteDepartmentsByCity(city: String): Response<List<Department>>
}
