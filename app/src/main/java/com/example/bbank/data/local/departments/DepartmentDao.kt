package com.example.bbank.data.local.departments

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
internal interface DepartmentDao {
    @Query("SELECT * FROM departments")
    suspend fun getAllLocalDepartments(): List<DepartmentEntity>?

    @Query("SELECT * FROM departments WHERE id = :id")
    suspend fun getLocalDepartmentsById(id: Long): DepartmentEntity?

    @Query("SELECT * FROM departments WHERE name = :cityName")
    suspend fun getLocalDepartmentsByCity(cityName: String): List<DepartmentEntity>?

    @Insert
    suspend fun insertLocalDepartment(departmentEntity: DepartmentEntity)

    @Query("DELETE FROM departments")
    suspend fun deleteAllLocalDepartments()
}
