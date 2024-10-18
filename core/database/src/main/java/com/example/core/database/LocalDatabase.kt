package com.example.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.database.dao.DepartmentDao
import com.example.core.database.dao.NewsDao
import com.example.core.database.entity.DepartmentEntity
import com.example.core.database.entity.NewsEntity

@Database(
    entities = [
        NewsEntity::class,
        DepartmentEntity::class,
//        CurrencyRatesEntity::class
    ],
    version = 1
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun departmentDao(): DepartmentDao
//    abstract fun currencyRatesDao(): CurrencyRatesDao
}