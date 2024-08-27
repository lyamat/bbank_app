package com.example.bbank.data.repositories

import com.example.bbank.data.local.news.NewsEntity
import com.example.bbank.data.remote.dto.NewsResponseDto
import com.example.bbank.data.repositories.local.NewsLocal
import com.example.bbank.data.repositories.remote.NewsRemote
import com.example.bbank.domain.networking.orNullResponseError
import com.example.bbank.domain.repositories.NewsRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class NewsRepositoryImpl @Inject constructor(
    private val newsLocal: NewsLocal,
    private val newsRemote: NewsRemote
) : NewsRepository {
    override suspend fun getLocalNews(): List<NewsEntity> =
        newsLocal.getLocalNews() ?: listOf(NewsEntity.empty())

    override suspend fun getLocalNewsById(id: Long): NewsEntity =
        newsLocal.getLocalNewsById(id) ?: NewsEntity.empty()

    override suspend fun saveToLocalNews(newsEntity: NewsEntity) =
        newsLocal.saveToLocalNews(newsEntity)

    override suspend fun deleteAllLocalNews() {
        newsLocal.deleteAllLocalNews()
    }

    override suspend fun getRemoteNews(): Response<List<NewsResponseDto>> =
        newsRemote.getLast200News().orNullResponseError()
}
