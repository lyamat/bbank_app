package com.example.core.domain.news

import com.example.core.domain.util.DataError
import com.example.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(): Flow<List<News>>
    suspend fun getNewsById(id: String): Flow<News>
    suspend fun upsertNews(news: List<News>)
    suspend fun deleteAllNews()

    suspend fun fetchNews(): EmptyResult<DataError>
}