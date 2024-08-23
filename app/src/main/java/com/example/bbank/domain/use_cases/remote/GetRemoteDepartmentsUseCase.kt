package com.example.bbank.domain.use_cases.remote

import com.example.bbank.data.remote.dto.toDepartment
import com.example.bbank.domain.models.Department
import com.example.bbank.domain.networking.DataError
import com.example.bbank.domain.networking.Result
import com.example.bbank.domain.networking.safeApiCall
import com.example.bbank.domain.repositories.RemoteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetRemoteDepartmentsByCityUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(city: String): Result<List<Department>, DataError.Network> {
        val result = safeApiCall {
            remoteRepository.getRemoteDepartmentsByCity(city)
        }
        return when (result) {
            is Result.Success -> {
                val departmentList = result.data.map { it.toDepartment() }
                Result.Success(departmentList)
            }

            is Result.Error -> {
                Result.Error(result.error)
            }
        }
    }
}