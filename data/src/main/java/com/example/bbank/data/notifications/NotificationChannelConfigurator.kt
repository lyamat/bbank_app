package com.example.bbank.data.notifications

import android.app.NotificationChannel

internal interface NotificationChannelConfigurator {
    fun configureChannel(channel: NotificationChannel)
}