package com.example.bbank.domain.repositories

import com.example.bbank.data.remote.dto.NewsResponseDto

interface RemoteRepository {
    suspend fun getRemoteNews(): List<NewsResponseDto>?
}