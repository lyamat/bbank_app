package com.example.news.data.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.example.core.domain.news.News
import com.example.core.domain.notifications.NewsNotificationService
import com.example.news.data.R
import com.example.news.data.notifications.NewsNotificationChannelFactory.Companion.NEWS_CHANNEL_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsNotificationServiceImpl @Inject constructor(
    private val notificationManager: NotificationManager,
    private val notificationChannelFactory: NewsNotificationChannelFactory,
    @ApplicationContext private val context: Context
) : NewsNotificationService {

    override suspend fun showNewsNotification(news: News) {
        notificationChannelFactory.createNewsNotificationChannel()
        withContext(Dispatchers.IO) {
            val deepLinkUri = Uri.parse("app://com.example.app/newsDetail?newsLink=${news.link}")
            val intent = Intent(Intent.ACTION_VIEW, deepLinkUri).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val notification = NotificationCompat.Builder(context, NEWS_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_news)
                .setContentTitle(context.getString(R.string.news_of, news.startDate))
                .setContentText(news.nameRu)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            notificationManager.notify(1, notification)
        }
    }

    override suspend fun cancelNewsNotifications() {
        TODO("Not yet implemented")
    }
}
