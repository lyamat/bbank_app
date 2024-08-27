package com.example.bbank.di.modules

import android.content.SharedPreferences
import com.example.bbank.data.local.currency_rates.CurrencyRatesDao
import com.example.bbank.data.local.departments.DepartmentDao
import com.example.bbank.data.local.news.NewsDao
import com.example.bbank.data.remote.BelarusBankApi
import com.example.bbank.data.repositories.CityRepositoryImpl
import com.example.bbank.data.repositories.CurrencyRepositoryImpl
import com.example.bbank.data.repositories.DepartmentRepositoryImpl
import com.example.bbank.data.repositories.NewsRepositoryImpl
import com.example.bbank.data.repositories.local.CurrencyRatesLocal
import com.example.bbank.data.repositories.local.CurrencyRatesLocalImpl
import com.example.bbank.data.repositories.local.DepartmentLocal
import com.example.bbank.data.repositories.local.DepartmentLocalImpl
import com.example.bbank.data.repositories.local.NewsLocal
import com.example.bbank.data.repositories.local.NewsLocalImpl
import com.example.bbank.data.repositories.local.SharedPreferencesLocal
import com.example.bbank.data.repositories.local.SharedPreferencesLocalImpl
import com.example.bbank.data.repositories.remote.DepartmentRemote
import com.example.bbank.data.repositories.remote.DepartmentRemoteImpl
import com.example.bbank.data.repositories.remote.NewsRemote
import com.example.bbank.data.repositories.remote.NewsRemoteImpl
import com.example.bbank.domain.repositories.CityRepository
import com.example.bbank.domain.repositories.CurrencyRepository
import com.example.bbank.domain.repositories.DepartmentRepository
import com.example.bbank.domain.repositories.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class RepositoryModule {
    @Singleton
    @Provides
    internal fun provideNewsRepository(
        newsLocal: NewsLocal,
        newsRemote: NewsRemote
    ): NewsRepository = NewsRepositoryImpl(newsLocal, newsRemote)

    @Singleton
    @Provides
    internal fun provideDepartmentRepository(
        departmentLocal: DepartmentLocal,
        departmentRemote: DepartmentRemote
    ): DepartmentRepository = DepartmentRepositoryImpl(departmentLocal, departmentRemote)

    @Singleton
    @Provides
    internal fun provideCurrencyRepository(
        currencyRatesLocal: CurrencyRatesLocal,
        sharedPreferencesLocal: SharedPreferencesLocal
    ): CurrencyRepository = CurrencyRepositoryImpl(currencyRatesLocal, sharedPreferencesLocal)

    @Singleton
    @Provides
    internal fun provideCityRepository(
        sharedPreferencesLocal: SharedPreferencesLocal
    ): CityRepository = CityRepositoryImpl(sharedPreferencesLocal)

    @Singleton
    @Provides
    internal fun provideNewsRemote(
        belarusBankApi: BelarusBankApi
    ): NewsRemote = NewsRemoteImpl(belarusBankApi)

    @Singleton
    @Provides
    internal fun provideDepartmentsRemote(
        belarusBankApi: BelarusBankApi
    ): DepartmentRemote = DepartmentRemoteImpl(belarusBankApi)

    @Singleton
    @Provides
    internal fun provideNewsLocal(
        newsDao: NewsDao
    ): NewsLocal = NewsLocalImpl(newsDao)

    @Singleton
    @Provides
    internal fun provideDepartmentsLocal(
        departmentDao: DepartmentDao
    ): DepartmentLocal = DepartmentLocalImpl(departmentDao)

    @Singleton
    @Provides
    internal fun provideCurrencyRatesLocal(
        currencyRatesDao: CurrencyRatesDao
    ): CurrencyRatesLocal = CurrencyRatesLocalImpl(currencyRatesDao)

    @Singleton
    @Provides
    internal fun provideSharedPreferencesLocal(
        sharedPreferences: SharedPreferences
    ): SharedPreferencesLocal = SharedPreferencesLocalImpl(sharedPreferences)
}