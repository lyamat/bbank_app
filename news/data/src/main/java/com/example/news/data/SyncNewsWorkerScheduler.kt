package com.example.news.data

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.await
import com.example.core.domain.news.SyncNewsScheduler
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.toJavaDuration

class SyncNewsWorkerScheduler @Inject constructor(
    @ApplicationContext private val context: Context,
) : SyncNewsScheduler {

    private val workManager = WorkManager.getInstance(context)

    override suspend fun scheduleSync(type: SyncNewsScheduler.SyncType) {
        when (type) {
            is SyncNewsScheduler.SyncType.FetchNews -> scheduleFetchNewsWorker(type.interval)
        }
    }

    private suspend fun scheduleFetchNewsWorker(interval: Duration) {
        val workInfos = withContext(Dispatchers.IO) {
            workManager.getWorkInfosForUniqueWork("news_sync_work").get()
        }

        if (workInfos.isEmpty() || workInfos.first().state == WorkInfo.State.CANCELLED) {
            val workRequest = PeriodicWorkRequestBuilder<FetchNewsWorker>(
                repeatInterval = interval.toJavaDuration()
            )
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .setBackoffCriteria(
                    backoffPolicy = BackoffPolicy.EXPONENTIAL,
                    backoffDelay = 2000L,
                    timeUnit = TimeUnit.MILLISECONDS
                )
                .setInitialDelay(
                    duration = 24,
                    timeUnit = TimeUnit.HOURS
                )
                .build()

            workManager.enqueueUniquePeriodicWork(
                "news_sync_work",
                ExistingPeriodicWorkPolicy.UPDATE, workRequest
            ).await()
        }
    }

    override suspend fun cancelSync() {
        workManager
            .cancelUniqueWork("news_sync_work")
            .await()
    }
}