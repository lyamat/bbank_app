package com.example.bbank.di.modules

import android.content.Context
import com.example.bbank.data.local.NewsDao
import com.example.bbank.data.remote.BelarusBankApi
import com.example.bbank.data.repositories.LocalRepositoryImpl
import com.example.bbank.data.repositories.RemoteRepositoryImpl
import com.example.bbank.data.repositories.local.NewsLocal
import com.example.bbank.data.repositories.local.NewsLocalImpl
import com.example.bbank.data.repositories.remote.ExchangesRemote
import com.example.bbank.data.repositories.remote.ExchangesRemoteImpl
import com.example.bbank.data.repositories.remote.NewsRemote
import com.example.bbank.data.repositories.remote.NewsRemoteImpl
import com.example.bbank.domain.repositories.LocalRepository
import com.example.bbank.domain.repositories.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class RepositoryModule {
    @Singleton
    @Provides
    internal fun provideRemoteRepository(
        @ApplicationContext context: Context, // TODO: remove this after test exchanges
        newsRemote: NewsRemote,
        exchangesRemote: ExchangesRemote,
    ): RemoteRepository = RemoteRepositoryImpl(newsRemote, exchangesRemote, context)

    @Singleton
    @Provides
    internal fun provideLocalRepository(
        newsLocal: NewsLocal
    ): LocalRepository = LocalRepositoryImpl(newsLocal)

    @Singleton
    @Provides
    internal fun provideNewsRemote(
        belarusBankApi: BelarusBankApi
    ): NewsRemote = NewsRemoteImpl(belarusBankApi)

    @Singleton
    @Provides
    internal fun provideExchangesRemote(
        belarusBankApi: BelarusBankApi
    ): ExchangesRemote = ExchangesRemoteImpl(belarusBankApi)

    @Singleton
    @Provides
    internal fun providePostsLocal(newsDao: NewsDao):
            NewsLocal = NewsLocalImpl(newsDao)

}