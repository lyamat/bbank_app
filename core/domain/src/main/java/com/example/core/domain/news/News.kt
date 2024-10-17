package com.example.core.domain.news

data class News(
    val id: String?,
    val nameRu: String,
    val htmlRu: String,
    val img: String,
    val startDate: String,
    val link: String
)