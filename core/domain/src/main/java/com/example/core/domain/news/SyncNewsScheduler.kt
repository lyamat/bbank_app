package com.example.core.domain.news

import kotlin.time.Duration

interface SyncNewsScheduler {

    suspend fun scheduleSync(type: SyncType)
    suspend fun cancelSync()

    sealed interface SyncType {
        data class FetchNews(val interval: Duration) : SyncType
    }
}