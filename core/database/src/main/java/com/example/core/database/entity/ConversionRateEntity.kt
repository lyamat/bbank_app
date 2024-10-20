package com.example.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ConversionRateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fromCurrency: String,
    val toCurrency: String,
    val rateIn: Double,
    val rateOut: Double
)