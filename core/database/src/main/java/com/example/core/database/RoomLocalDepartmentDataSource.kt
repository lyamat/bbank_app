package com.example.core.database

import android.database.sqlite.SQLiteFullException
import com.example.core.database.dao.DepartmentDao
import com.example.core.database.mapper.toDepartment
import com.example.core.database.mapper.toDepartmentEntity
import com.example.core.domain.department.Department
import com.example.core.domain.department.DepartmentId
import com.example.core.domain.department.LocalDepartmentDataSource
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalDepartmentDataSource(
    private val departmentDao: DepartmentDao
) : LocalDepartmentDataSource {
    override fun getDepartments(): Flow<List<Department>> {
        return departmentDao.getDepartments()
            .map { departmentEntities ->
                departmentEntities.map { it.toDepartment() }
            }
    }

    override fun getDepartmentById(id: String): Flow<Department> {
        return departmentDao.getDepartmentById(id).map { it.toDepartment() }
    }

    override fun getDepartmentsByCity(cityName: String): Flow<List<Department>> {
        return departmentDao.getDepartmentByCity(cityName)
            .map { departmentEntities ->
                departmentEntities.map { it.toDepartment() }
            }
    }

    override suspend fun insertDepartments(departments: List<Department>): Result<List<DepartmentId>, DataError.Local> {
        return try {
            val entities = departments.map { it.toDepartmentEntity() }
            departmentDao.insertDepartments(entities)
            Result.Success(entities.map { it.id })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteAllDepartments() {
        departmentDao.deleteAllDepartments()
    }
}