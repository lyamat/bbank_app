package com.example.bbank.data.remote.dto

import com.example.bbank.data.local.NewsEntity
import com.example.bbank.domain.models.News
import com.google.gson.annotations.SerializedName

data class NewsResponseDto(
    @SerializedName("name_ru")
    val nameRu: String,
    @SerializedName("html_ru")
    val htmlRu: String,
    @SerializedName("img")
    val img: String,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("link")
    val link: String
)

fun NewsResponseDto.toNews() =
    News(
        nameRu = nameRu,
        htmlRu = htmlRu,
        img = img,
        startDate = startDate,
        link = link,
    )

fun NewsResponseDto.toNewsEntity() =
    NewsEntity(
        nameRu = nameRu,
        htmlRu = htmlRu,
        img = img,
        startDate = startDate,
        link = link,
    )