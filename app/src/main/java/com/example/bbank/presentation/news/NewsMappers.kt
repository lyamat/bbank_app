package com.example.bbank.presentation.news

import com.example.core.domain.news.News

internal fun News.toNewsParcelable() =
    NewsParcelable(
        id = id ?: "",
        nameRu = nameRu,
        htmlRu = htmlRu,
        img = img,
        startDate = startDate,
        link = link,
    )

internal fun NewsParcelable.toNews() =
    News(
        id = id,
        nameRu = nameRu,
        htmlRu = htmlRu,
        img = img,
        startDate = startDate,
        link = link,
    )