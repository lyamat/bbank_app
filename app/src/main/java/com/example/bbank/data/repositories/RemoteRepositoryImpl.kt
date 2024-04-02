package com.example.bbank.data.repositories

import com.example.bbank.data.remote.dto.ExchangesResponseDto
import com.example.bbank.data.remote.dto.NewsResponseDto
import com.example.bbank.data.repositories.remote.ExchangesRemote
import com.example.bbank.data.repositories.remote.NewsRemote
import com.example.bbank.domain.repositories.RemoteRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteRepositoryImpl @Inject constructor(
    private val newsRemote: NewsRemote,
    private val exchangesRemote: ExchangesRemote
) : RemoteRepository {
    override suspend fun getRemoteNews(): List<NewsResponseDto> =
        newsRemote.getLast200News()?.body() ?: listOf(NewsResponseDto.empty())

    override suspend fun getRemoteExchangesByCity(city: String): List<ExchangesResponseDto> =
        exchangesRemote.getRemoteExchangeByCity(city)?.body() ?: listOf(ExchangesResponseDto.empty())
}