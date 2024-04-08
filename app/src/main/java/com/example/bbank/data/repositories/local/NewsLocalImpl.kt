package com.example.bbank.data.repositories.local

import com.example.bbank.data.local.news.NewsDao
import com.example.bbank.data.local.news.NewsEntity
import javax.inject.Inject

internal class NewsLocalImpl @Inject constructor(
    private val newsDao: NewsDao
) : NewsLocal {
    override suspend fun getLocalNews(): List<NewsEntity>? =
        newsDao.getAllLocalNews()

    override suspend fun getLocalNewsById(id: Long): NewsEntity? =
        newsDao.getLocalNewsById(id)

    override suspend fun saveToLocalNews(newsEntity: NewsEntity) {
        newsDao.insertLocalNews(newsEntity)
    }

    override suspend fun deleteAllLocalNews() {
        newsDao.deleteAllLocalNews()
    }
}