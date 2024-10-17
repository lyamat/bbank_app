package com.example.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.bson.types.ObjectId

@Entity
data class NewsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String = ObjectId().toHexString(),
    val nameRu: String,
    val htmlRu: String,
    val img: String,
    val startDate: String,
    val link: String
)