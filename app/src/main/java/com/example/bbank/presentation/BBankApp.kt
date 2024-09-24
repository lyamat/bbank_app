package com.example.bbank.presentation

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.bbank.BuildConfig
import com.example.bbank.data.notifications.news.NewsNotificationChannelFactory
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
internal class BBankApp : Application(), Configuration.Provider {
    @Inject
    lateinit var newsNotificationChannelFactory: NewsNotificationChannelFactory

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        newsNotificationChannelFactory.createNotificationChannel()
    }

    @Inject
    lateinit var myWorkerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(myWorkerFactory)
            .build()
    }
}