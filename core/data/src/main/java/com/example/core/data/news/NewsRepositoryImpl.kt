package com.example.core.data.news

import com.example.core.domain.news.LocalNewsDataSource
import com.example.core.domain.news.News
import com.example.core.domain.news.NewsRepository
import com.example.core.domain.news.RemoteNewsDataSource
import com.example.core.domain.util.DataError
import com.example.core.domain.util.EmptyResult
import com.example.core.domain.util.Result
import com.example.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val localNewsDataSource: LocalNewsDataSource,
    private val remoteNewsDataSource: RemoteNewsDataSource,
    private val applicationScope: CoroutineScope
) : NewsRepository {
    override fun getNews(): Flow<List<News>> {
        return localNewsDataSource.getNews()
    }

    override suspend fun getNewsById(id: String): Flow<News> {
        return localNewsDataSource.getNewsById(id)
    }

    override suspend fun upsertNews(news: List<News>) {
        localNewsDataSource.upsertNews(news)
    }

    override suspend fun deleteAllNews() {
        localNewsDataSource.deleteAllNews()
    }

    override suspend fun fetchNews(): EmptyResult<DataError> {
        return when (val result = remoteNewsDataSource.getNews()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localNewsDataSource.upsertNews(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }
}
