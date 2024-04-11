package com.example.bbank.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bbank.data.local.departments.DepartmentDao
import com.example.bbank.data.local.departments.DepartmentEntity
import com.example.bbank.data.local.news.NewsDao
import com.example.bbank.data.local.news.NewsEntity

@Database(
    entities = [
        NewsEntity::class,
        DepartmentEntity::class],
    version = 1
)
internal abstract class LocalDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun departmentDao(): DepartmentDao
}