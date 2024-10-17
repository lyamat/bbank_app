//package com.example.bbank.data.repositories.local

//import com.example.bbank.data.local.departments.DepartmentDao
//import com.example.bbank.data.local.departments.DepartmentEntity
//import javax.inject.Inject
//
//class DepartmentLocalImpl @Inject constructor(
//    private val departmentDao: DepartmentDao
//) : DepartmentLocal {
//    override suspend fun getAllLocalDepartments(): List<DepartmentEntity>? =
//        departmentDao.getAllLocalDepartments()
//
//    override suspend fun getLocalDepartmentById(id: Long): DepartmentEntity? =
//        departmentDao.getLocalDepartmentsById(id)
//
//    override suspend fun getLocalDepartmentsByCity(cityName: String): List<DepartmentEntity>? =
//        departmentDao.getLocalDepartmentsByCity(cityName)
//
//    override suspend fun saveToLocalDepartments(departmentEntity: DepartmentEntity) {
//        departmentDao.insertLocalDepartment(departmentEntity)
//    }
//
//    override suspend fun deleteAllLocalDepartments() {
//        departmentDao.deleteAllLocalDepartments()
//    }
//}