package com.example.core.domain.news

import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result

interface RemoteNewsDataSource {
    suspend fun getNews(): Result<List<News>, DataError.Network>
}