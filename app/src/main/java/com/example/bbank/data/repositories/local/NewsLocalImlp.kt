package com.example.bbank.data.repositories.local

import com.example.bbank.data.local.NewsDao
import com.example.bbank.data.local.NewsEntity
import javax.inject.Inject

internal class NewsLocalImpl @Inject constructor(
    private val newsDao: NewsDao
) : NewsLocal {
    override suspend fun getLocalPosts(): List<NewsEntity>? =
        newsDao.getAll()

    override suspend fun getById(id: Long): NewsEntity? =
        newsDao.getById(id)

    override suspend fun insertPost(newsEntity: NewsEntity) {
        newsDao.insert(newsEntity)
    }

    override suspend fun deleteAll() {
        newsDao.deleteAll()
    }
}