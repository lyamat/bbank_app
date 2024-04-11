package com.example.bbank.domain.repositories

import com.example.bbank.data.remote.dto.DepartmentResponseDto
import com.example.bbank.data.remote.dto.NewsResponseDto

internal interface RemoteRepository {
    suspend fun getRemoteNews(): List<NewsResponseDto>
    suspend fun getRemoteDepartmentsByCity(city: String): List<DepartmentResponseDto>
}