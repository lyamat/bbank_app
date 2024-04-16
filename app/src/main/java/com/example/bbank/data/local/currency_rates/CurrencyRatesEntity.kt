package com.example.bbank.data.local.currency_rates

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bbank.domain.models.CurrencyRates

@Entity(tableName = "currency_rates")
internal data class CurrencyRatesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "usdIn")
    val usdIn: String?,
    @ColumnInfo(name = "usdOut")
    val usdOut: String?,
    @ColumnInfo(name = "eurIn")
    val eurIn: String?,
    @ColumnInfo(name = "eurOut")
    val eurOut: String?,
    @ColumnInfo(name = "rubIn")
    val rubIn: String?,
    @ColumnInfo(name = "rubOut")
    val rubOut: String?,
    @ColumnInfo(name = "gbpIn")
    val gbpIn: String?,
    @ColumnInfo(name = "gbpOut")
    val gbpOut: String?,
    @ColumnInfo(name = "cadIn")
    val cadIn: String?,
    @ColumnInfo(name = "cadOut")
    val cadOut: String?,
    @ColumnInfo(name = "plnIn")
    val plnIn: String?,
    @ColumnInfo(name = "plnOut")
    val plnOut: String?,
    @ColumnInfo(name = "sekIn")
    val sekIn: String?,
    @ColumnInfo(name = "sekOut")
    val sekOut: String?,
    @ColumnInfo(name = "chfIn")
    val chfIn: String?,
    @ColumnInfo(name = "chfOut")
    val chfOut: String?,
    @ColumnInfo(name = "usdEurIn")
    val usdEurIn: String?,
    @ColumnInfo(name = "usdEurOut")
    val usdEurOut: String?,
    @ColumnInfo(name = "usdRubIn")
    val usdRubIn: String?,
    @ColumnInfo(name = "usdRubOut")
    val usdRubOut: String?,
    @ColumnInfo(name = "rubEurIn")
    val rubEurIn: String?,
    @ColumnInfo(name = "rubEurOut")
    val rubEurOut: String?,
    @ColumnInfo(name = "jpyIn")
    val jpyIn: String?,
    @ColumnInfo(name = "jpyOut")
    val jpyOut: String?,
    @ColumnInfo(name = "cnyIn")
    val cnyIn: String?,
    @ColumnInfo(name = "cnyOut")
    val cnyOut: String?,
    @ColumnInfo(name = "cnyEurIn")
    val cnyEurIn: String?,
    @ColumnInfo(name = "cnyEurOut")
    val cnyEurOut: String?,
    @ColumnInfo(name = "cnyUsdIn")
    val cnyUsdIn: String?,
    @ColumnInfo(name = "cnyUsdOut")
    val cnyUsdOut: String?,
    @ColumnInfo(name = "cnyRubIn")
    val cnyRubIn: String?,
    @ColumnInfo(name = "cnyRubOut")
    val cnyRubOut: String?,
    @ColumnInfo(name = "czkIn")
    val czkIn: String?,
    @ColumnInfo(name = "czkOut")
    val czkOut: String?,
    @ColumnInfo(name = "nokIn")
    val nokIn: String?,
    @ColumnInfo(name = "nokOut")
    val nokOut: String?
) {
    companion object {
        fun empty(): CurrencyRatesEntity =
            CurrencyRatesEntity(
                usdIn = "",
                usdOut = "",
                eurIn = "",
                eurOut = "",
                rubIn = "",
                rubOut = "",
                gbpIn = "",
                gbpOut = "",
                cadIn = "",
                cadOut = "",
                plnIn = "",
                plnOut = "",
                sekIn = "",
                sekOut = "",
                chfIn = "",
                chfOut = "",
                usdEurIn = "",
                usdEurOut = "",
                usdRubIn = "",
                usdRubOut = "",
                rubEurIn = "",
                rubEurOut = "",
                jpyIn = "",
                jpyOut = "",
                cnyIn = "",
                cnyOut = "",
                cnyEurIn = "",
                cnyEurOut = "",
                cnyUsdIn = "",
                cnyUsdOut = "",
                cnyRubIn = "",
                cnyRubOut = "",
                czkIn = "",
                czkOut = "",
                nokIn = "",
                nokOut = ""
            )
    }
}

internal fun CurrencyRatesEntity.toCurrencyRates() =
    CurrencyRates(
        usdIn = usdIn ?: "",
        usdOut = usdOut ?: "",
        eurIn = eurIn ?: "",
        eurOut = eurOut ?: "",
        rubIn = rubIn ?: "",
        rubOut = rubOut ?: "",
        gbpIn = gbpIn ?: "",
        gbpOut = gbpOut ?: "",
        cadIn = cadIn ?: "",
        cadOut = cadOut ?: "",
        plnIn = plnIn ?: "",
        plnOut = plnOut ?: "",
        sekIn = sekIn ?: "",
        sekOut = sekOut ?: "",
        chfIn = chfIn ?: "",
        chfOut = chfOut ?: "",
        usdEurIn = usdEurIn ?: "",
        usdEurOut = usdEurOut ?: "",
        usdRubIn = usdRubIn ?: "",
        usdRubOut = usdRubOut ?: "",
        rubEurIn = rubEurIn ?: "",
        rubEurOut = rubEurOut ?: "",
        jpyIn = jpyIn ?: "",
        jpyOut = jpyOut ?: "",
        cnyIn = cnyIn ?: "",
        cnyOut = cnyOut ?: "",
        cnyEurIn = cnyEurIn ?: "",
        cnyEurOut = cnyEurOut ?: "",
        cnyUsdIn = cnyUsdIn ?: "",
        cnyUsdOut = cnyUsdOut ?: "",
        cnyRubIn = cnyRubIn ?: "",
        cnyRubOut = cnyRubOut ?: "",
        czkIn = czkIn ?: "",
        czkOut = czkOut ?: "",
        nokIn = nokIn ?: "",
        nokOut = nokOut ?: ""
    )