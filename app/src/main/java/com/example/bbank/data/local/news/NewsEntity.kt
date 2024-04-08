package com.example.bbank.data.local.news

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bbank.domain.models.News

@Entity(tableName = "news")
internal data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "nameRu")
    val nameRu: String?,
    @ColumnInfo(name = "htmlRu")
    val htmlRu: String?,
    @ColumnInfo(name = "img")
    val img: String?,
    @ColumnInfo(name = "startDate")
    val startDate: String?,
    @ColumnInfo(name = "link")
    val link: String?
) {
    companion object {
        fun empty(): NewsEntity =
            NewsEntity(
                nameRu = "",
                htmlRu = "",
                img = "",
                startDate = "",
                link = "",
            )
    }
}

internal fun NewsEntity.toNews() =
    News(
        nameRu = nameRu ?: "",
        htmlRu = htmlRu ?: "",
        img = img ?: "",
        startDate = startDate ?: "",
        link = link ?: "",
    )
