package com.example.bbank.domain.use_cases.remote

import com.example.bbank.data.remote.dto.toNews
import com.example.bbank.domain.models.News
import com.example.bbank.domain.networking.DataError
import com.example.bbank.domain.networking.Result
import com.example.bbank.domain.networking.safeApiCall
import com.example.bbank.domain.repositories.RemoteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetRemoteNewsUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(): Result<List<News>, DataError.Network> {
        val result = safeApiCall {
            remoteRepository.getRemoteNews()
        }
        return when (result) {
            is Result.Success -> {
                val newsList = result.data.map { it.toNews() }
                Result.Success(newsList)
            }

            is Result.Error -> {
                Result.Error(result.error)
            }
        }
    }
}