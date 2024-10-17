package com.example.bbank.presentation.news

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsParcelable(
    val id: String,
    val nameRu: String,
    val htmlRu: String,
    val img: String,
    val startDate: String,
    val link: String
) : Parcelable