package com.example.bbank.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NewsEntity::class],
    version = 1
)
internal abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}