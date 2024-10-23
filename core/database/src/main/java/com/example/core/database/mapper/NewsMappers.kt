package com.example.core.database.mapper

import com.example.core.database.entity.NewsEntity
import com.example.core.domain.news.News

fun NewsEntity.toNews(): News {
    return News(
        nameRu = nameRu,
        htmlRu = htmlRu,
        img = img,
        startDate = startDate,
        link = link,
    )
}

fun News.toNewsEntity(): NewsEntity {
    return NewsEntity(
        nameRu = nameRu,
        htmlRu = htmlRu,
        img = img,
        startDate = startDate,
        link = link,
    )
}
