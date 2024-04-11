package com.example.bbank.data.repositories

import com.example.bbank.data.remote.dto.DepartmentResponseDto
import com.example.bbank.data.remote.dto.NewsResponseDto
import com.example.bbank.data.repositories.remote.DepartmentRemote
import com.example.bbank.data.repositories.remote.NewsRemote
import com.example.bbank.domain.repositories.RemoteRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class RemoteRepositoryImpl @Inject constructor(
    private val newsRemote: NewsRemote,
    private val departmentRemote: DepartmentRemote
) : RemoteRepository {
    override suspend fun getRemoteNews(): List<NewsResponseDto> =
        newsRemote.getLast200News()?.body() ?: listOf(NewsResponseDto.empty())

    override suspend fun getRemoteDepartmentsByCity(city: String): List<DepartmentResponseDto> =
        departmentRemote.getRemoteDepartmentsByCity(city)?.body()
            ?: listOf(DepartmentResponseDto.empty())
}