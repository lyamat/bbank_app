package com.example.bbank.domain.models

import com.example.bbank.data.local.NewsEntity


internal data class News(
    val nameRu: String,
    val htmlRu: String,
    val img: String,
    val startDate: String,
    val link: String
)

internal fun News.toNewsEntity() =
    NewsEntity(
        nameRu = nameRu,
        htmlRu = htmlRu,
        img = img,
        startDate = startDate,
        link = link,
    )
