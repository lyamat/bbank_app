package com.example.bbank.presentation.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.example.bbank.R
import com.example.bbank.domain.models.News
import com.example.bbank.presentation.activity.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class NewsNotificationService @Inject constructor(
    @ApplicationContext private val context: Context
) : NotificationService<News> {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        const val NEWS_CHANNEL_ID = "news_channel"
    }

    override fun showNotification(data: News) {
        val deepLinkIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.newsFragment)
            .setArguments(bundleOf("newsId" to data.link))
            .createPendingIntent()

        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, NEWS_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_news)
            .setContentTitle("News of ${data.startDate}")
            .setContentText(data.nameRu)
            .setContentIntent(activityPendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
}