package com.example.core.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class NotificationChannelFactory(
    @ApplicationContext val context: Context,
    private val notificationManager: NotificationManager
) {
    protected suspend fun createNotificationChannel(
        id: String,
        name: String,
        importance: Int,
        description: String
    ) = withContext(Dispatchers.IO) {
        if (notificationManager.getNotificationChannel(id) != null) {
            return@withContext
        }
        val channel = NotificationChannel(id, name, importance).apply {
            this.description = description
        }
        notificationManager.createNotificationChannel(channel)
    }
}