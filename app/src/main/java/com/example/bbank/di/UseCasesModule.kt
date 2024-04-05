package com.example.bbank.di

import com.example.bbank.domain.repositories.LocalRepository
import com.example.bbank.domain.repositories.RemoteRepository
import com.example.bbank.domain.use_cases.GetLocalUseCase
import com.example.bbank.domain.use_cases.GetRemoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class UseCasesModule {

    @Singleton
    @Provides
    internal fun provideGetLocalUseCase(localRepository: LocalRepository) =
        GetLocalUseCase(localRepository = localRepository)

    @Singleton
    @Provides
    internal fun provideGetRemoteUseCase(remoteRepository: RemoteRepository) =
        GetRemoteUseCase(remoteRepository = remoteRepository)
}