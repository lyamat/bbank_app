package com.example.bbank.data.remote

import com.example.bbank.data.remote.dto.ExchangeRateResponseDto
import com.example.bbank.data.remote.dto.NewsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BelarusBankApi {

    companion object {
        private const val CITY = "city"
    }

    @GET("news_info/")
    suspend fun getLatestNews(): List<NewsResponseDto>?

    @GET("kursExchange/")
    suspend fun getExchangeRateByCity(
        @Query(CITY) city: String
    ): Response<List<ExchangeRateResponseDto>>
}
