package com.example.news.network

import com.example.core.data.network.get
import com.example.core.domain.news.News
import com.example.core.domain.news.RemoteNewsDataSource
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.core.domain.util.map
import io.ktor.client.HttpClient

class KtorRemoteNewsDataSource(
    private val httpClient: HttpClient
) : RemoteNewsDataSource {
    override suspend fun getNews(): Result<List<News>, DataError.Network> {
        return httpClient.get<List<NewsDto>>(
            route = "/news_info"
        ).map { newsDtos ->
            newsDtos.map { it.toNews() }
        }
    }
}