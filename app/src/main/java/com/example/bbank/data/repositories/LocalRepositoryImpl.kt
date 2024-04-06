package com.example.bbank.data.repositories

import com.example.bbank.data.local.NewsEntity
import com.example.bbank.data.repositories.local.NewsLocal
import com.example.bbank.domain.repositories.LocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class LocalRepositoryImpl @Inject constructor(
    private val newsLocal: NewsLocal
) : LocalRepository {
    override suspend fun getLocalNews(): List<NewsEntity> =
        newsLocal.getLocalPosts() ?: listOf(NewsEntity.empty())

    override suspend fun getNewById(id: Long): NewsEntity =
        newsLocal.getById(id = id) ?: NewsEntity.empty()

    override suspend fun savePost(newsEntity: NewsEntity) {
        newsLocal.insertPost(newsEntity)
    }
}