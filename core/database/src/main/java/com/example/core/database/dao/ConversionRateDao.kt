package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.core.database.entity.ConversionRateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversionRateDao {
    @Upsert
    suspend fun upsertConversionRates(conversionRates: List<ConversionRateEntity>)

    @Query("SELECT * FROM conversionrateentity")
    fun getConversionRates(): Flow<List<ConversionRateEntity>>
}