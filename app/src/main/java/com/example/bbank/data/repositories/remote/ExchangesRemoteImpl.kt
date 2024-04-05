package com.example.bbank.data.repositories.remote

import com.example.bbank.data.remote.BelarusBankApi
import com.example.bbank.data.remote.dto.ExchangesResponseDto
import retrofit2.Response
import javax.inject.Inject

internal class ExchangesRemoteImpl @Inject constructor(
    private val belarusBankApi: BelarusBankApi
) : ExchangesRemote {
    override suspend fun getRemoteExchangeByCity(city: String): Response<List<ExchangesResponseDto>>? =
        belarusBankApi.getExchangesByCity(city)
}