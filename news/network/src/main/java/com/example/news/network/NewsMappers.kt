package com.example.news.network

import com.example.core.domain.news.News
import org.bson.types.ObjectId

fun NewsDto.toNews(): News {
    return News(
        nameRu = nameRu ?: "",
        htmlRu = htmlRu ?: "",
        img = img ?: "",
        startDate = startDate ?: "",
        link = link ?: ObjectId().toHexString(),
    )
}