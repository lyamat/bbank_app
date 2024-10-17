package com.example.news.network.di

import com.example.core.domain.news.RemoteNewsDataSource
import com.example.news.network.KtorRemoteNewsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsNetworkModule {
    @Singleton
    @Provides
    fun provideRemoteNewsDataSource(
        httpClient: HttpClient
    ): RemoteNewsDataSource = KtorRemoteNewsDataSource(httpClient = httpClient)
}