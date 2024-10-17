//package com.example.bbank.data.repositories
//
//import com.example.bbank.data.local.departments.DepartmentEntity
//import com.example.bbank.data.local.departments.DepartmentEntity.Companion.toDepartment
//import com.example.bbank.data.local.departments.DepartmentEntity.Companion.toDepartmentEntity
//import com.example.bbank.data.remote.dto.DepartmentResponseDto.Companion.toDepartment
//import com.example.bbank.data.repositories.local.DepartmentLocal
//import com.example.bbank.data.repositories.remote.DepartmentRemote
//import com.example.bbank.domain.models.Department
//import com.example.bbank.domain.repository.DepartmentRepository
//import retrofit2.Response
//import javax.inject.Inject
//import javax.inject.Singleton
//
//@Singleton
//class DepartmentRepositoryImpl @Inject constructor(
////    private val departmentLocal: DepartmentLocal,
////    private val departmentRemote: DepartmentRemote
//) : DepartmentRepository {
//    override suspend fun getAllLocalDepartments(): List<Department> =
//        departmentLocal.getAllLocalDepartments()?.map { it.toDepartment() }
//            ?: listOf(DepartmentEntity.empty().toDepartment())
//
//    override suspend fun getLocalDepartmentById(id: Long): Department =
//        departmentLocal.getLocalDepartmentById(id)?.toDepartment()
//            ?: DepartmentEntity.empty().toDepartment()
//
//    override suspend fun getLocalDepartmentsByCity(cityName: String): List<Department> =
//        if (cityName.isBlank()) {
//            departmentLocal.getAllLocalDepartments()?.map { it.toDepartment() }
//                ?: listOf(DepartmentEntity.empty().toDepartment())
//        } else {
//            departmentLocal.getLocalDepartmentsByCity(cityName)?.map { it.toDepartment() }
//                ?: listOf(DepartmentEntity.empty().toDepartment())
//        }
//
//    override suspend fun saveToLocalDepartments(department: Department) =
//        departmentLocal.saveToLocalDepartments(department.toDepartmentEntity())
//
//    override suspend fun deleteAllLocalDepartments() =
//        departmentLocal.deleteAllLocalDepartments()
//
//    override suspend fun getRemoteDepartmentsByCity(city: String): Response<List<Department>> {
//        val response = departmentRemote.getRemoteDepartmentsByCity(city)
//        return if (response.isSuccessful && response.body() != null) {
//            val departments = response.body()!!.map { it.toDepartment() }
//            Response.success(departments)
//        } else {
//            Response.error(response.code(), response.errorBody()!!)
//        }
//    }
//}
