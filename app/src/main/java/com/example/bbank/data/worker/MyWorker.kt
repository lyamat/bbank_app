package com.example.bbank.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.bbank.domain.use_cases.local.GetLocalNewsUseCase
import com.example.bbank.presentation.utils.NewsNotificationService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
internal class MyWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val newsNotificationService: NewsNotificationService,
    private val getLocalNewsUseCase: GetLocalNewsUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val allLocalNews = getLocalNewsUseCase()
        val tmpOneRandomNews = allLocalNews.shuffled().first()

        newsNotificationService.showNotification(tmpOneRandomNews)

        Log.d("Lyamat", "MyWorker: ${tmpOneRandomNews.nameRu}")
        return Result.success()
    }
}