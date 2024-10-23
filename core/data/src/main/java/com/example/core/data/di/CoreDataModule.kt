package com.example.core.data.di

import com.example.core.data.converter.ConverterRepositoryImpl
import com.example.core.data.department.DepartmentRepositoryImpl
import com.example.core.data.news.NewsRepositoryImpl
import com.example.core.domain.converter.ConverterRepository
import com.example.core.domain.converter.LocalConverterDataSource
import com.example.core.domain.department.DepartmentRepository
import com.example.core.domain.department.LocalDepartmentDataSource
import com.example.core.domain.department.RemoteDepartmentDataSource
import com.example.core.domain.news.LocalNewsDataSource
import com.example.core.domain.news.NewsRepository
import com.example.core.domain.news.RemoteNewsDataSource
import com.example.core.domain.shared_preferences.SharedPreferencesLocal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
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
            engine {
                requestTimeout = 30_000
                endpoint {
                    connectTimeout = 15_000
                    connectAttempts = 5
                    keepAliveTime = 5000L
                }
            }
        }

    @Singleton
    @Provides
    fun provideNewsRepository(
        localNewsDataSource: LocalNewsDataSource,
        remoteNewsDataSource: RemoteNewsDataSource,
        applicationScope: CoroutineScope
    ): NewsRepository =
        NewsRepositoryImpl(localNewsDataSource, remoteNewsDataSource, applicationScope)

    @Singleton
    @Provides
    fun provideDepartmentRepository(
        localDepartmentDataSource: LocalDepartmentDataSource,
        remoteDepartmentDataSource: RemoteDepartmentDataSource,
        localConverterDataSource: LocalConverterDataSource,
        applicationScope: CoroutineScope
    ): DepartmentRepository =
        DepartmentRepositoryImpl(
            localDepartmentDataSource,
            remoteDepartmentDataSource,
            localConverterDataSource,
            applicationScope
        )

    @Singleton
    @Provides
    fun provideConverterRepository(
        localConverterDataSource: LocalConverterDataSource,
        sharedPreferencesLocal: SharedPreferencesLocal
    ): ConverterRepository =
        ConverterRepositoryImpl(localConverterDataSource, sharedPreferencesLocal)
}