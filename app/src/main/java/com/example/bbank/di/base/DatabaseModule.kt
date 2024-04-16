package com.example.bbank.di.base

import android.app.Application
import androidx.room.Room
import com.example.bbank.data.local.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Singleton
    @Provides
    internal fun provideDataBase(
        context: Application
    ): LocalDatabase =
        Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "bbank_db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    internal fun provideNewsDao(
        db: LocalDatabase
    ) = db.newsDao()

    @Singleton
    @Provides
    internal fun provideDepartmentsDao(
        db: LocalDatabase
    ) = db.departmentDao()

    @Singleton
    @Provides
    internal fun provideCurrencyRatesDao(
        db: LocalDatabase
    ) = db.currencyRatesDao()
}