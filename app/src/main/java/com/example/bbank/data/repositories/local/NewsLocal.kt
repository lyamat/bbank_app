package com.example.bbank.data.repositories.local

import com.example.bbank.data.local.news.NewsEntity

internal interface NewsLocal {
    suspend fun getLocalNews(): List<NewsEntity>?
    suspend fun getLocalNewsById(id: Long): NewsEntity?
    suspend fun saveToLocalNews(newsEntity: NewsEntity)
    suspend fun deleteAllLocalNews()
}