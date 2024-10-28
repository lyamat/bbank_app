package com.example.news.data.notifications

import android.app.NotificationManager
import android.content.Context
import com.example.core.data.notification.NotificationChannelFactory
import com.example.news.data.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsNotificationChannelFactory @Inject constructor(
    @ApplicationContext context: Context,
    notificationManager: NotificationManager
) : NotificationChannelFactory(context, notificationManager) {

    override suspend fun createNewsNotificationChannel() = withContext(Dispatchers.IO) {
        createNotificationChannel(
            NEWS_CHANNEL_ID,
            context.getString(R.string.news),
            NotificationManager.IMPORTANCE_DEFAULT,
            context.getString(R.string.for_last_news)
        )
    }

    companion object {
        const val NEWS_CHANNEL_ID = "NEWS_CHANNEL"
    }
}

