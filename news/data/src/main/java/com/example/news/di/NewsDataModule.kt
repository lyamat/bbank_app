package com.example.news.di

import android.app.NotificationManager
import android.content.Context
import com.example.core.domain.news.SyncNewsScheduler
import com.example.core.domain.notifications.NewsNotificationService
import com.example.news.data.SyncNewsWorkerScheduler
import com.example.news.data.notifications.NewsNotificationChannelFactory
import com.example.news.data.notifications.NewsNotificationServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsDataModule {
    @Singleton
    @Provides
    fun provideSyncNewsScheduler(
        @ApplicationContext context: Context
    ): SyncNewsScheduler = SyncNewsWorkerScheduler(context = context)

    @Singleton
    @Provides
    fun provideNewsNotificationService(
        notificationManager: NotificationManager,
        notificationChannelFactory: NewsNotificationChannelFactory,
        @ApplicationContext context: Context
    ): NewsNotificationService =
        NewsNotificationServiceImpl(notificationManager, notificationChannelFactory, context)
}