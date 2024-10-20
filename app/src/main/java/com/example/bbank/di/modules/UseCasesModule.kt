package com.example.bbank.di.modules

import com.example.bbank.domain.usecase.local.GetCurrentCityUseCase
import com.example.bbank.domain.usecase.local.SaveCurrentCityUseCase
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
    internal fun provideGetCurrentCityUseCase() =
        GetCurrentCityUseCase()

    @Singleton
    @Provides
    internal fun provideSaveCurrentCityUseCase() =
        SaveCurrentCityUseCase()
}
