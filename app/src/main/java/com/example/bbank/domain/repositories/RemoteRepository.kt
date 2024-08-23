package com.example.bbank.domain.repositories

import com.example.bbank.data.remote.dto.DepartmentResponseDto
import com.example.bbank.data.remote.dto.NewsResponseDto
import retrofit2.Response

internal interface RemoteRepository {
    suspend fun getRemoteNews(): Response<List<NewsResponseDto>>
    suspend fun getRemoteDepartmentsByCity(city: String): Response<List<DepartmentResponseDto>>
}