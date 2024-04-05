package com.example.bbank.di.base

import android.app.Application
import androidx.room.Room
import com.example.bbank.data.local.NewsDatabase
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
    internal fun provideDataBase(context: Application): NewsDatabase =
        Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news_db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    internal fun provideDao(db: NewsDatabase) = db.newsDao()

//    @Provides
//    fun provideEntity() = NewsEntity()
}