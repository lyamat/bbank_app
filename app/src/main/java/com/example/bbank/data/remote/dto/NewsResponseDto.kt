package com.example.bbank.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NewsResponseDto(
    @SerializedName("name_ru") val nameRu: String?,
    @SerializedName("html_ru") val htmlRu: String?,
    @SerializedName("img") val img: String?,
    @SerializedName("start_date") val startDate: String?,
    @SerializedName("link") val link: String?
) //
// TODO: add a function to return an empty object in case of an incorrect answer