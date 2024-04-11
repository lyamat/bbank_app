package com.example.bbank.di.modules

import android.content.SharedPreferences
import com.example.bbank.data.local.departments.DepartmentDao
import com.example.bbank.data.local.news.NewsDao
import com.example.bbank.data.remote.BelarusBankApi
import com.example.bbank.data.repositories.LocalRepositoryImpl
import com.example.bbank.data.repositories.RemoteRepositoryImpl
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
import com.example.bbank.domain.repositories.LocalRepository
import com.example.bbank.domain.repositories.RemoteRepository
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
    internal fun provideRemoteRepository(
        newsRemote: NewsRemote,
        departmentRemote: DepartmentRemote,
    ): RemoteRepository = RemoteRepositoryImpl(newsRemote, departmentRemote)

    @Singleton
    @Provides
    internal fun provideLocalRepository(
        newsLocal: NewsLocal,
        departmentLocal: DepartmentLocal,
        sharedPreferencesLocal: SharedPreferencesLocal
    ): LocalRepository = LocalRepositoryImpl(newsLocal, departmentLocal, sharedPreferencesLocal)

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
    internal fun provideSharedPreferencesLocal(
        sharedPreferences: SharedPreferences
    ): SharedPreferencesLocal = SharedPreferencesLocalImpl(sharedPreferences)
}