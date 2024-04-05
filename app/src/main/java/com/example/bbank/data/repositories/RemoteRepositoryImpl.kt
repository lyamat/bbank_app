package com.example.bbank.data.repositories

import android.content.Context
import com.example.bbank.data.remote.dto.ExchangesResponseDto
import com.example.bbank.data.remote.dto.NewsResponseDto
import com.example.bbank.data.repositories.remote.ExchangesRemote
import com.example.bbank.data.repositories.remote.NewsRemote
import com.example.bbank.domain.repositories.RemoteRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class RemoteRepositoryImpl @Inject constructor(
    private val newsRemote: NewsRemote,
    private val exchangesRemote: ExchangesRemote,
    private val context: Context, // TODO: remove this after test exchanges
) : RemoteRepository {
    override suspend fun getRemoteNews(): List<NewsResponseDto> =
        newsRemote.getLast200News()?.body() ?: listOf(NewsResponseDto.empty())

    override suspend fun getRemoteExchangesByCity(city: String): List<ExchangesResponseDto> =
        getTestExchangesRateData()
//        exchangesRemote.getRemoteExchangeByCity(city)?.body()
//            ?: listOf(ExchangesResponseDto.empty())

    private fun getTestExchangesRateData() : List<ExchangesResponseDto> {
        val gson = Gson()
        val listType = object : TypeToken<List<ExchangesResponseDto>>() {}.type

        val resourceId = context.resources.getIdentifier("exchanges_rate", "raw", context.packageName)
        val inputStream = context.resources.openRawResource(resourceId)
        val reader = InputStreamReader(inputStream)

        return gson.fromJson(reader, listType)
    }

    // TODO: some logic for getRemoteExchangesByCity!!!
}