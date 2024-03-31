package com.example.bbank.domain.repositories

import com.example.bbank.domain.models.News

interface RemoteRepository {
    suspend fun getNews(): List<News>
}