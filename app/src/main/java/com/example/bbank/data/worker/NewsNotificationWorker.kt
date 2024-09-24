package com.example.bbank.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.bbank.data.notifications.news.NewsNotificationService
import com.example.bbank.domain.use_cases.local.GetLocalNewsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
internal class NewsNotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val newsNotificationService: NewsNotificationService,
    private val getLocalNewsUseCase: GetLocalNewsUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        try {
            val allLocalNews = getLocalNewsUseCase()
            val tmpOneRandomNews = allLocalNews.shuffled().first()
            newsNotificationService.showNotification(tmpOneRandomNews)
        } catch (e: Exception) {
            return Result.failure();
        }
        return Result.success()
    }
}