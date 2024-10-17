package com.example.bbank.data.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext

internal abstract class BaseNotificationChannelFactory(
    @ApplicationContext val context: Context,
    private val notificationManager: NotificationManager
) {
    abstract fun createNotificationChannel(configurator: NotificationChannelConfigurator? = null)

    protected fun createNotificationChannel(
        id: String,
        name: String,
        importance: Int,
        description: String,
        configurator: NotificationChannelConfigurator? = null
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(id, name, importance)
            channel.description = description
            configurator?.configureChannel(channel)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
