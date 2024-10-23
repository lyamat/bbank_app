package com.example.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsEntity(
    @PrimaryKey(autoGenerate = false)
    val link: String,
    val nameRu: String,
    val htmlRu: String,
    val img: String,
    val startDate: String
)