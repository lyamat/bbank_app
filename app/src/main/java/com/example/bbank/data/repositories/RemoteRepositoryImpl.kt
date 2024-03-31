package com.example.bbank.data.repositories

import com.example.bbank.data.repositories.remote.NewsRemote
import com.example.bbank.domain.models.News
import com.example.bbank.domain.repositories.RemoteRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteRepositoryImpl @Inject constructor(
    private val newsRemote: NewsRemote
) : RemoteRepository {

    override suspend fun getNews(): List<News> {
        val newsResponseDtoList = newsRemote.getSomeNews()?.body() ?: emptyList()
        return newsResponseDtoList.map { dto ->
            News(
                nameRu = dto.nameRu ?: "",
                htmlRu = dto.htmlRu ?: "",
                img = dto.img ?: "",
                startDate = dto.startDate ?: "",
                link = dto.link ?: ""
            )
        }
    }
}