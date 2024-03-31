package com.example.bbank.di.module

import com.example.bbank.data.repositories.RemoteRepositoryImpl
import com.example.bbank.data.repositories.remote.NewsRemote
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
        newsRemote: NewsRemote
    ): RemoteRepository = RemoteRepositoryImpl(newsRemote)

}