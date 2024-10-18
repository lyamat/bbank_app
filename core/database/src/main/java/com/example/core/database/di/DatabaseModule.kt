package com.example.core.database.di

import android.app.Application
import androidx.room.Room
import com.example.core.database.LocalDatabase
import com.example.core.database.RoomLocalDepartmentDataSource
import com.example.core.database.RoomLocalNewsDataSource
import com.example.core.database.dao.DepartmentDao
import com.example.core.database.dao.NewsDao
import com.example.core.domain.department.LocalDepartmentDataSource
import com.example.core.domain.news.LocalNewsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDataBase(
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
    fun provideNewsDao(
        db: LocalDatabase
    ): NewsDao = db.newsDao()

    @Singleton
    @Provides
    fun provideDepartmentDao(
        db: LocalDatabase
    ): DepartmentDao = db.departmentDao()

    @Singleton
    @Provides
    fun provideLocalNewsDataSource(
        newsDao: NewsDao
    ): LocalNewsDataSource = RoomLocalNewsDataSource(newsDao)

    @Singleton
    @Provides
    fun provideLocalDepartmentDataSource(
        departmentDao: DepartmentDao
    ): LocalDepartmentDataSource = RoomLocalDepartmentDataSource(departmentDao)
}