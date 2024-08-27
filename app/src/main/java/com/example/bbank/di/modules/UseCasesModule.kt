package com.example.bbank.di.modules

import com.example.bbank.domain.repositories.CityRepository
import com.example.bbank.domain.repositories.CurrencyRepository
import com.example.bbank.domain.repositories.DepartmentRepository
import com.example.bbank.domain.repositories.NewsRepository
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
    internal fun provideGetRemoteDepartmentsByCityUseCase(departmentRepository: DepartmentRepository) =
        GetRemoteDepartmentsByCityUseCase(departmentRepository)

    @Singleton
    @Provides
    internal fun provideGetLocalDepartmentsByCityUseCase(departmentRepository: DepartmentRepository) =
        GetLocalDepartmentsByCityUseCase(departmentRepository)

    @Singleton
    @Provides
    internal fun provideSaveToLocalDepartmentsUseCase(departmentRepository: DepartmentRepository) =
        SaveToLocalDepartmentsUseCase(departmentRepository)

    @Singleton
    @Provides
    internal fun provideDeleteAllLocalDepartmentsUseCase(departmentRepository: DepartmentRepository) =
        DeleteAllLocalDepartmentsUseCase(departmentRepository)

    @Singleton
    @Provides
    internal fun provideGetRemoteNewsUseCase(newsRepository: NewsRepository) =
        GetRemoteNewsUseCase(newsRepository)

    @Singleton
    @Provides
    internal fun provideGetLocalNewsUseCase(newsRepository: NewsRepository) =
        GetLocalNewsUseCase(newsRepository)

    @Singleton
    @Provides
    internal fun provideSaveToLocalNewsUseCase(newsRepository: NewsRepository) =
        SaveToLocalNewsUseCase(newsRepository)

    @Singleton
    @Provides
    internal fun provideDeleteAllLocalNewsUseCase(newsRepository: NewsRepository) =
        DeleteAllLocalNewsUseCase(newsRepository)

    @Singleton
    @Provides
    internal fun provideGetLocalCurrencyRatesUseCase(currencyRepository: CurrencyRepository) =
        GetLocalCurrencyRatesUseCase(currencyRepository)

    @Singleton
    @Provides
    internal fun provideSaveToLocalCurrencyRatesUseCase(currencyRepository: CurrencyRepository) =
        SaveToLocalCurrencyRatesUseCase(currencyRepository)

    @Singleton
    @Provides
    internal fun provideDeleteAllCurrencyRatesUseCase(currencyRepository: CurrencyRepository) =
        DeleteAllCurrencyRatesUseCase(currencyRepository)


    @Singleton
    @Provides
    internal fun provideGetCurrencyValuesUseCase(currencyRepository: CurrencyRepository) =
        GetCurrencyValuesUseCase(currencyRepository)

    @Singleton
    @Provides
    internal fun provideSetCurrencyValuesUseCase(currencyRepository: CurrencyRepository) =
        SetCurrencyValuesUseCase(currencyRepository)

    @Singleton
    @Provides
    internal fun provideGetCurrentCityUseCase(cityRepository: CityRepository) =
        GetCurrentCityUseCase(cityRepository)

    @Singleton
    @Provides
    internal fun provideSaveCurrentCityUseCase(cityRepository: CityRepository) =
        SaveCurrentCityUseCase(cityRepository)
}
