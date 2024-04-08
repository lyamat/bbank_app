package com.example.bbank.data.local.news

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
internal interface NewsDao {
    @Query("SELECT * FROM news")
    suspend fun getAllLocalNews(): List<NewsEntity>?

    @Query("SELECT * FROM news WHERE id = :id")
    suspend fun getLocalNewsById(id: Long): NewsEntity?

    @Insert
    suspend fun insertLocalNews(newsEntity: NewsEntity)

    @Query("DELETE FROM news")
    suspend fun deleteAllLocalNews()
}
