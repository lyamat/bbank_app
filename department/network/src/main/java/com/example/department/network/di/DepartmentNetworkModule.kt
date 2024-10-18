package com.example.department.network.di

import com.example.core.domain.department.RemoteDepartmentDataSource
import com.example.department.network.KtorRemoteDepartmentDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DepartmentNetworkModule {
    @Singleton
    @Provides
    fun provideRemoteDepartmentDataSource(
        httpClient: HttpClient
    ): RemoteDepartmentDataSource = KtorRemoteDepartmentDataSource(httpClient)
}