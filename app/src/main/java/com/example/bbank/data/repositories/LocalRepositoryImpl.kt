package com.example.bbank.data.repositories

import com.example.bbank.data.local.NewsEntity
import com.example.bbank.data.repositories.local.NewsLocal
import com.example.bbank.domain.repositories.LocalRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocalRepositoryImpl @Inject constructor(
    private val newsLocal: NewsLocal
) : LocalRepository {

    override suspend fun getLocalNews(): List<NewsEntity>? =
        newsLocal.getLocalPosts()

    override suspend fun getNewById(id: Long): NewsEntity? =
        newsLocal.getById(id = id)

}