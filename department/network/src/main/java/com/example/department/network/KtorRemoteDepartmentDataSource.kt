package com.example.department.network

import com.example.core.data.network.get
import com.example.core.domain.department.Department
import com.example.core.domain.department.RemoteDepartmentDataSource
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.core.domain.util.map
import io.ktor.client.HttpClient

class KtorRemoteDepartmentDataSource(
    private val httpClient: HttpClient
) : RemoteDepartmentDataSource {
    override suspend fun getDepartments(): Result<List<Department>, DataError.Network> {
        return httpClient.get<List<DepartmentDto>>(
            route = "/kursExchange"
        ).map { departmentDtos ->
            departmentDtos.map { it.toDepartment() }
        }
    }
}