package com.example.bbank.data.remote.dto

import com.example.bbank.domain.models.News
import com.google.gson.annotations.SerializedName

internal data class NewsResponseDto(
    @SerializedName("name_ru")
    val nameRu: String?,
    @SerializedName("html_ru")
    val htmlRu: String?,
    @SerializedName("img")
    val img: String?,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("link")
    val link: String?
) {
    companion object {
        fun empty() : NewsResponseDto =
            NewsResponseDto(
                nameRu = "",
                htmlRu = "",
                img = "",
                startDate = "",
                link = ""
            )
    }
}

internal fun NewsResponseDto.toNews() =
    News(
        nameRu = nameRu ?: "",
        htmlRu = htmlRu ?: "",
        img = img ?: "",
        startDate = startDate ?: "",
        link = link ?: "",
    )