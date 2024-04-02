package com.example.bbank.data.repositories.remote

import com.example.bbank.data.remote.dto.NewsResponseDto
import retrofit2.Response

interface NewsRemote {
    suspend fun getLast200News(): Response<List<NewsResponseDto>>?
}