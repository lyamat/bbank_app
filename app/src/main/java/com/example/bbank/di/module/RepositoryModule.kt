package com.example.bbank.di.module

import com.example.bbank.data.repositories.LocalRepositoryImpl
import com.example.bbank.data.repositories.RemoteRepositoryImpl
import com.example.bbank.data.repositories.local.NewsLocal
import com.example.bbank.data.repositories.remote.NewsRemote
import com.example.bbank.domain.repositories.LocalRepository
import com.example.bbank.domain.repositories.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRemoteRepository(
        newsLocal: NewsLocal,
        newsRemote: NewsRemote
    ): RemoteRepository = RemoteRepositoryImpl(newsLocal, newsRemote)

    @Singleton
    @Provides
    fun provideLocalRepository(
        newsLocal: NewsLocal
    ): LocalRepository = LocalRepositoryImpl(newsLocal)

}