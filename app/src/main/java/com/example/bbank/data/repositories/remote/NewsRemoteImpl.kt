package com.example.bbank.data.repositories.remote

import com.example.bbank.data.remote.BelarusBankApi
import com.example.bbank.data.remote.dto.NewsResponseDto
import retrofit2.Response
import javax.inject.Inject

class NewsRemoteImpl @Inject constructor(
    private val belarusBankApi: BelarusBankApi
) : NewsRemote {
    override suspend fun getSomeNews(): Response<List<NewsResponseDto>>? =
        belarusBankApi.getLatestNews()
}