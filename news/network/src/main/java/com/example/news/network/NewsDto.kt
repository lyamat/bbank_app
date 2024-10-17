package com.example.news.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsDto(
    @SerialName("name_ru")
    val nameRu: String?,
    @SerialName("html_ru")
    val htmlRu: String?,
    @SerialName("img")
    val img: String?,
    @SerialName("start_date")
    val startDate: String?,
    @SerialName("link")
    val link: String?
)