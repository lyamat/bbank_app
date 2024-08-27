package com.example.bbank.domain.repositories

import com.example.bbank.data.local.news.NewsEntity
import com.example.bbank.domain.models.News
import retrofit2.Response

internal interface NewsRepository {
    suspend fun getLocalNews(): List<NewsEntity>
    suspend fun getLocalNewsById(id: Long): NewsEntity
    suspend fun saveToLocalNews(newsEntity: NewsEntity)
    suspend fun deleteAllLocalNews()

    suspend fun getRemoteNews(): Response<List<News>>
}
