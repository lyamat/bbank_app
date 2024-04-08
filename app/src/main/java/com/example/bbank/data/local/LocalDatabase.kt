package com.example.bbank.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bbank.data.local.exchanges.ExchangesDao
import com.example.bbank.data.local.exchanges.ExchangesEntity
import com.example.bbank.data.local.news.NewsDao
import com.example.bbank.data.local.news.NewsEntity

@Database(
    entities = [
        NewsEntity::class,
        ExchangesEntity::class],
    version = 1
)
internal abstract class LocalDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun exchangesDao(): ExchangesDao
}