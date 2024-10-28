package com.example.core.domain.news

import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

typealias NewsLink = String

interface LocalNewsDataSource {
    fun getNews(): Flow<List<News>>
    fun getNewsByLink(link: String): Flow<News>
    fun getLatestNews(): News
    suspend fun upsertNews(news: List<News>): Result<List<NewsLink>, DataError.Local>
    suspend fun deleteAllNews()
}