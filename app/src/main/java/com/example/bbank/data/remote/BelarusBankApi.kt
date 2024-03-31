package com.example.bbank.data.remote

import com.example.bbank.data.remote.dto.ExchangeRateResponseDto
import com.example.bbank.data.remote.dto.NewsResponseDto
import retrofit2.Response
import retrofit2.http.*

interface BelarusBankApi {

    companion object {
        private const val CITY = "city"
    }

    @GET("news_info/")
    suspend fun getLatestNews(): Response<List<NewsResponseDto>>?

    @GET("kursExchange/")
    suspend fun getExchangeRateByCity(
        @Query(CITY) city: String
    ): Response<List<ExchangeRateResponseDto>>
}
