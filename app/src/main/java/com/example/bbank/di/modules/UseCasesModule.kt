package com.example.bbank.di.modules

import com.example.bbank.domain.usecase.local.DeleteAllCurrencyRatesUseCase
import com.example.bbank.domain.usecase.local.DeleteAllLocalDepartmentsUseCase
import com.example.bbank.domain.usecase.local.GetCurrencyValuesUseCase
import com.example.bbank.domain.usecase.local.GetCurrentCityUseCase
import com.example.bbank.domain.usecase.local.GetLocalCurrencyRatesUseCase
import com.example.bbank.domain.usecase.local.SaveCurrentCityUseCase
import com.example.bbank.domain.usecase.local.SetCurrencyValuesUseCase
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
    internal fun provideDeleteAllLocalDepartmentsUseCase() =
        DeleteAllLocalDepartmentsUseCase()

    @Singleton
    @Provides
    internal fun provideGetLocalCurrencyRatesUseCase() =
        GetLocalCurrencyRatesUseCase()

    @Singleton
    @Provides
    internal fun provideDeleteAllCurrencyRatesUseCase() =
        DeleteAllCurrencyRatesUseCase()

    @Singleton
    @Provides
    internal fun provideGetCurrencyValuesUseCase() =
        GetCurrencyValuesUseCase()

    @Singleton
    @Provides
    internal fun provideSetCurrencyValuesUseCase() =
        SetCurrencyValuesUseCase()

    @Singleton
    @Provides
    internal fun provideGetCurrentCityUseCase() =
        GetCurrentCityUseCase()

    @Singleton
    @Provides
    internal fun provideSaveCurrentCityUseCase() =
        SaveCurrentCityUseCase()
}
