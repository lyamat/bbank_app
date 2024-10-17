package com.example.core.database.mapper

import com.example.core.database.entity.NewsEntity
import com.example.core.domain.news.News
import org.bson.types.ObjectId

fun NewsEntity.toNews(): News {
    return News(
        id = id,
        nameRu = nameRu,
        htmlRu = htmlRu,
        img = img,
        startDate = startDate,
        link = link,
    )
}

fun News.toNewsEntity(): NewsEntity {
    return NewsEntity(
        id = id ?: ObjectId().toHexString(),
        nameRu = nameRu,
        htmlRu = htmlRu,
        img = img,
        startDate = startDate,
        link = link,
    )
}
