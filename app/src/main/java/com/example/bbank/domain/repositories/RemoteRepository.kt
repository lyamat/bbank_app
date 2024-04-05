package com.example.bbank.domain.repositories

import com.example.bbank.data.remote.dto.ExchangesResponseDto
import com.example.bbank.data.remote.dto.NewsResponseDto

internal interface RemoteRepository {
    suspend fun getRemoteNews(): List<NewsResponseDto>
    suspend fun getRemoteExchangesByCity(city: String): List<ExchangesResponseDto>
}