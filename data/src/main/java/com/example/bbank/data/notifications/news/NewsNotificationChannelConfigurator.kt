package com.example.bbank.data.notifications.news

import android.app.NotificationChannel
import android.os.Build
import com.example.bbank.data.notifications.NotificationChannelConfigurator

internal class NewsNotificationChannelConfigurator : NotificationChannelConfigurator {
    override fun configureChannel(channel: NotificationChannel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel.enableVibration(true)
            channel.setSound(null, null)
            channel.enableLights(true)
        }
    }
}
