package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.core.database.entity.DepartmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DepartmentDao {
    @Insert
    suspend fun insertDepartments(departments: List<DepartmentEntity>)

    @Query("SELECT * FROM departmententity")
    fun getDepartments(): Flow<List<DepartmentEntity>>

    @Query("SELECT * FROM departmententity WHERE id = :id")
    fun getDepartmentById(id: String): Flow<DepartmentEntity>

    @Query("DELETE FROM departmententity")
    suspend fun deleteAllDepartments()

    @Query("SELECT * FROM departmententity WHERE name = :cityName")
    fun getDepartmentByCity(cityName: String): Flow<List<DepartmentEntity>>
}