package com.example.bbank.presentation

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.bbank.BuildConfig
import com.example.bbank.presentation.utils.NewsNotificationService
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
internal class BBankApp : Application(), Configuration.Provider {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        createNotificationChannel()
    }

    // TODO: make in di, maybe as factory 
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NewsNotificationService.NEWS_CHANNEL_ID,
                "News",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Used for showing last news"

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @Inject
    lateinit var myWorkerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(myWorkerFactory)
            .build()
    }
}