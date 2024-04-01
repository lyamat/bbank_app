package com.example.bbank.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    suspend fun getAll(): List<NewsEntity>?

    @Query("SELECT * FROM news WHERE id = :id")
    suspend fun getById(id: Long): NewsEntity?

    @Insert
    suspend fun insert(newsEntity: NewsEntity)

    @Query("DELETE FROM news")
    suspend fun deleteAll()
}
