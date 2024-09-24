package com.example.bbank.data.notifications.news

import android.app.NotificationManager
import android.content.Context
import com.example.bbank.R
import com.example.bbank.data.notifications.BaseNotificationChannelFactory
import com.example.bbank.data.notifications.NotificationChannelConfigurator
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class NewsNotificationChannelFactory @Inject constructor(
    @ApplicationContext context: Context,
    notificationManager: NotificationManager
) : BaseNotificationChannelFactory(context, notificationManager) {
    override fun createNotificationChannel(configurator: NotificationChannelConfigurator?) {
        createNotificationChannel(
            NewsNotificationService.NEWS_CHANNEL_ID,
            context.getString(R.string.news),
            NotificationManager.IMPORTANCE_DEFAULT,
            context.getString(R.string.for_showing_last_news),
            configurator ?: NewsNotificationChannelConfigurator()
        )
    }
}

