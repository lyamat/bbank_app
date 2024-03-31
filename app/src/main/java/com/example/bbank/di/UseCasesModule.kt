package com.example.bbank.di

import com.example.bbank.domain.repositories.RemoteRepository
import com.example.bbank.domain.use_cases.GetNewsUseCase
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
    fun provideGetPostInfoUseCase(remoteRepository: RemoteRepository) =
        GetNewsUseCase(remoteRepository = remoteRepository)
}