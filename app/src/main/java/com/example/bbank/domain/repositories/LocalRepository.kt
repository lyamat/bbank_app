package com.example.bbank.domain.repositories

import com.example.bbank.data.local.NewsEntity

interface LocalRepository {
    suspend fun getLocalNews(): List<NewsEntity>
    suspend fun getNewById(id: Long): NewsEntity
}