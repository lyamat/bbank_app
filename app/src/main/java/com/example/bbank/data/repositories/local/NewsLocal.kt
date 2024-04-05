package com.example.bbank.data.repositories.local

import com.example.bbank.data.local.NewsEntity

internal interface NewsLocal {
    suspend fun getLocalPosts(): List<NewsEntity>?
    suspend fun getById(id: Long): NewsEntity?
    suspend fun insertPost(newsEntity: NewsEntity)
    suspend fun deleteAll(): Unit
}