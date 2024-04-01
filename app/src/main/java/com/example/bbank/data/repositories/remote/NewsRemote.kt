package com.example.bbank.data.repositories.remote

import com.example.bbank.data.remote.dto.NewsResponseDto

interface NewsRemote {
    suspend fun getLast200News(): List<NewsResponseDto>?
}