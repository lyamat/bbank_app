package com.example.core.database

import android.database.sqlite.SQLiteFullException
import com.example.core.database.dao.NewsDao
import com.example.core.database.mapper.toNews
import com.example.core.database.mapper.toNewsEntity
import com.example.core.domain.news.LocalNewsDataSource
import com.example.core.domain.news.News
import com.example.core.domain.news.NewsLink
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalNewsDataSource(
    private val newsDao: NewsDao
) : LocalNewsDataSource {

    override fun getNews(): Flow<List<News>> {
        return newsDao.getNews()
            .map { newsEntities ->
                newsEntities.map { it.toNews() }
            }
    }

    override fun getNewsByLink(link: String): Flow<News> {
        return newsDao.getNewsByLink(link).map { it.toNews() }
    }

    override suspend fun upsertNews(news: List<News>): Result<List<NewsLink>, DataError.Local> {
        return try {
            val entities = news.map { it.toNewsEntity() }
            newsDao.upsertNews(entities)
            Result.Success(entities.map { it.link })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteAllNews() {
        newsDao.deleteAllNews()
    }
}