package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.core.database.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Upsert
    suspend fun upsertNews(news: List<NewsEntity>)

    @Query("SELECT * FROM newsentity")
    fun getNews(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM newsentity WHERE id = :id")
    fun getNewsById(id: String): Flow<NewsEntity>

    @Query("DELETE FROM newsentity")
    suspend fun deleteAllNews()
}