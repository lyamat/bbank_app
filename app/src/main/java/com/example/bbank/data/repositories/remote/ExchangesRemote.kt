package com.example.bbank.data.repositories.remote

import com.example.bbank.data.remote.dto.ExchangesResponseDto
import retrofit2.Response

internal interface ExchangesRemote {
    suspend fun getRemoteExchangeByCity(city: String): Response<List<ExchangesResponseDto>>?
}