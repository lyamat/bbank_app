package com.example.bbank.di

import com.example.bbank.domain.repositories.LocalRepository
import com.example.bbank.domain.repositories.RemoteRepository
import com.example.bbank.domain.use_cases.GetLocalNewsUseCase
import com.example.bbank.domain.use_cases.GetRemoteNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {
    @Singleton
    @Provides
    fun provideGetRemoteNewsUseCase(remoteRepository: RemoteRepository) =
        GetRemoteNewsUseCase(remoteRepository = remoteRepository)

    @Singleton
    @Provides
    fun provideGetLocalNewByIdUseCase(localRepository: LocalRepository) =
        GetLocalNewsUseCase(localRepository = localRepository)
}