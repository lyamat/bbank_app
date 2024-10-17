package com.example.core.domain.news

import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

typealias NewsId = String

interface LocalNewsDataSource {
    fun getNews(): Flow<List<News>>
    fun getNewsById(id: String): Flow<News>
    suspend fun upsertNews(news: List<News>): Result<List<NewsId>, DataError.Local>
    suspend fun deleteAllNews()
}