package com.example.bbank.domain.models

import android.os.Parcelable
import com.example.bbank.data.local.news.NewsEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val nameRu: String,
    val htmlRu: String,
    val img: String,
    val startDate: String,
    val link: String
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as News

        if (nameRu != other.nameRu) return false
        if (htmlRu != other.htmlRu) return false
        if (img != other.img) return false
        if (startDate != other.startDate) return false
        if (link != other.link) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nameRu.hashCode()
        result = 31 * result + htmlRu.hashCode()
        result = 31 * result + img.hashCode()
        result = 31 * result + startDate.hashCode()
        result = 31 * result + link.hashCode()
        return result
    }
}

internal fun News.toNewsEntity() =
    NewsEntity(
        nameRu = nameRu,
        htmlRu = htmlRu,
        img = img,
        startDate = startDate,
        link = link,
    )
