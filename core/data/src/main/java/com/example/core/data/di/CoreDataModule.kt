package com.example.core.data.di

import com.example.core.data.news.NewsRepositoryImpl
import com.example.core.domain.news.LocalNewsDataSource
import com.example.core.domain.news.NewsRepository
import com.example.core.domain.news.RemoteNewsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreDataModule {
    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                level = LogLevel.BODY
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }

    @Singleton
    @Provides
    fun provideNewsRepository(
        localNewsDataSource: LocalNewsDataSource,
        remoteNewsDataSource: RemoteNewsDataSource,
        applicationScope: CoroutineScope
    ): NewsRepository = NewsRepositoryImpl(
        localNewsDataSource = localNewsDataSource,
        remoteNewsDataSource = remoteNewsDataSource,
        applicationScope = applicationScope
    )
}