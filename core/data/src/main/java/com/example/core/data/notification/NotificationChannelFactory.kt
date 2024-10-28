package com.example.core.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class NotificationChannelFactory(
    @ApplicationContext val context: Context,
    private val notificationManager: NotificationManager
) {
    abstract suspend fun createNewsNotificationChannel()

    protected suspend fun createNotificationChannel(
        id: String,
        name: String,
        importance: Int,
        description: String
    ) = withContext(Dispatchers.IO) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(id) != null) {
                return@withContext
            }
            val channel = NotificationChannel(id, name, importance).apply {
                this.description = description
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}
