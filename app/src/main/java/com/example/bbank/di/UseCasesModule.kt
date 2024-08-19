package com.example.bbank.di

import com.example.bbank.domain.repositories.LocalRepository
import com.example.bbank.domain.repositories.RemoteRepository
import com.example.bbank.domain.use_cases.local.DeleteAllCurrencyRatesUseCase
import com.example.bbank.domain.use_cases.local.DeleteAllLocalDepartmentsUseCase
import com.example.bbank.domain.use_cases.local.DeleteAllLocalNewsUseCase
import com.example.bbank.domain.use_cases.local.GetCurrencyValuesUseCase
import com.example.bbank.domain.use_cases.local.GetCurrentCityUseCase
import com.example.bbank.domain.use_cases.local.GetLocalCurrencyRatesUseCase
import com.example.bbank.domain.use_cases.local.GetLocalDepartmentsByCityUseCase
import com.example.bbank.domain.use_cases.local.GetLocalNewsUseCase
import com.example.bbank.domain.use_cases.local.SaveCurrentCityUseCase
import com.example.bbank.domain.use_cases.local.SaveToLocalCurrencyRatesUseCase
import com.example.bbank.domain.use_cases.local.SaveToLocalDepartmentsUseCase
import com.example.bbank.domain.use_cases.local.SaveToLocalNewsUseCase
import com.example.bbank.domain.use_cases.local.SetCurrencyValuesUseCase
import com.example.bbank.domain.use_cases.remote.GetRemoteDepartmentsByCityUseCase
import com.example.bbank.domain.use_cases.remote.GetRemoteNewsUseCase
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
    internal fun provideGetRemoteDepartmentsByCityUseCase(remoteRepository: RemoteRepository) =
        GetRemoteDepartmentsByCityUseCase(remoteRepository = remoteRepository)

    @Singleton
    @Provides
    internal fun provideGetRemoteNewsUseCase(remoteRepository: RemoteRepository) =
        GetRemoteNewsUseCase(remoteRepository = remoteRepository)

    @Singleton
    @Provides
    internal fun provideGetLocalNewsUseCase(localRepository: LocalRepository) =
        GetLocalNewsUseCase(localRepository = localRepository)

    @Singleton
    @Provides
    internal fun provideSaveToLocalNewsUseCase(localRepository: LocalRepository) =
        SaveToLocalNewsUseCase(localRepository = localRepository)

    @Singleton
    @Provides
    internal fun provideDeleteAllLocalNewsUseCase(localRepository: LocalRepository) =
        DeleteAllLocalNewsUseCase(localRepository = localRepository)

    @Singleton
    @Provides
    internal fun provideGetLocalDepartmentsByCityUseCase(localRepository: LocalRepository) =
        GetLocalDepartmentsByCityUseCase(localRepository = localRepository)

    @Singleton
    @Provides
    internal fun provideSaveToLocalDepartmentsUseCase(localRepository: LocalRepository) =
        SaveToLocalDepartmentsUseCase(localRepository = localRepository)

    @Singleton
    @Provides
    internal fun provideDeleteAllLocalDepartmentsUseCase(localRepository: LocalRepository) =
        DeleteAllLocalDepartmentsUseCase(localRepository = localRepository)

    @Singleton
    @Provides
    internal fun provideGetLocalCurrencyRatesUseCase(localRepository: LocalRepository) =
        GetLocalCurrencyRatesUseCase(localRepository = localRepository)

    @Singleton
    @Provides
    internal fun provideSaveToLocalCurrencyRatesUseCase(localRepository: LocalRepository) =
        SaveToLocalCurrencyRatesUseCase(localRepository = localRepository)

    @Singleton
    @Provides
    internal fun provideDeleteAllCurrencyRatesUseCase(localRepository: LocalRepository) =
        DeleteAllCurrencyRatesUseCase(localRepository = localRepository)

    @Singleton
    @Provides
    internal fun provideGetCurrentCityUseCase(localRepository: LocalRepository) =
        GetCurrentCityUseCase(localRepository = localRepository)

    @Singleton
    @Provides
    internal fun provideSaveCurrentCityUseCase(localRepository: LocalRepository) =
        SaveCurrentCityUseCase(localRepository = localRepository)

    @Singleton
    @Provides
    internal fun provideGetCurrencyValuesUseCase(localRepository: LocalRepository) =
        GetCurrencyValuesUseCase(localRepository = localRepository)

    @Singleton
    @Provides
    internal fun provideSetCurrencyValuesUseCase(localRepository: LocalRepository) =
        SetCurrencyValuesUseCase(localRepository = localRepository)
}
