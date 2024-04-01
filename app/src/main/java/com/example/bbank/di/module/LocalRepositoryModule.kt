package com.example.bbank.di.module

import com.example.bbank.data.local.NewsDao
import com.example.bbank.data.repositories.local.NewsLocal
import com.example.bbank.data.repositories.local.NewsLocalImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalRepositoryModule {

    @Singleton
    @Provides
    fun providePostsLocal(newsDao: NewsDao):
            NewsLocal = NewsLocalImpl(newsDao)
}