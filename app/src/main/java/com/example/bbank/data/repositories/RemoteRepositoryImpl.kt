package com.example.bbank.data.repositories

import com.example.bbank.data.remote.dto.NewsResponseDto
import com.example.bbank.data.remote.dto.toNewsEntity
import com.example.bbank.data.repositories.local.NewsLocal
import com.example.bbank.data.repositories.remote.NewsRemote
import com.example.bbank.domain.repositories.RemoteRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteRepositoryImpl @Inject constructor(
    private val newsLocal: NewsLocal,
    private val newsRemote: NewsRemote
) : RemoteRepository {

    override suspend fun getRemoteNews(): List<NewsResponseDto> {
        val news = newsRemote.getLast200News().orEmpty()

        if (news.isNotEmpty()) {
            newsLocal.deleteAll()
            news.forEach { new ->
                newsLocal.insertPost(new.toNewsEntity())
            }
        }
        return news
    }
}