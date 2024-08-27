package com.example.bbank.domain.use_cases.remote

import com.example.bbank.domain.networking.DataError
import com.example.bbank.domain.networking.Result
import com.example.bbank.domain.networking.safeApiCall
import retrofit2.Response

internal abstract class BaseRemoteUseCase<T, R>(
    private val repositoryCall: suspend () -> Response<List<T>>,
    private val mapper: (T) -> R
) {
    suspend operator fun invoke(): Result<List<R>, DataError.Network> {
        val result = safeApiCall { repositoryCall() }
        return when (result) {
            is Result.Success -> {
                val mappedList = result.data.map { mapper(it) }
                Result.Success(mappedList)
            }

            is Result.Error -> {
                Result.Error(result.error)
            }
        }
    }
}

