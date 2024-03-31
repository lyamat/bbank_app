package com.example.bbank.di.module

import com.example.bbank.data.remote.BelarusBankApi
import com.example.bbank.data.repositories.remote.NewsRemote
import com.example.bbank.data.repositories.remote.NewsRemoteImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteRepositoryModule {
    @Singleton
    @Provides
    fun provideNewsRemote(
        belarusBankApi: BelarusBankApi
    ): NewsRemote = NewsRemoteImpl(belarusBankApi)
}