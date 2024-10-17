package com.example.news.di

import android.content.Context
import com.example.core.domain.news.SyncNewsScheduler
import com.example.news.data.SyncNewsWorkerScheduler
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
}