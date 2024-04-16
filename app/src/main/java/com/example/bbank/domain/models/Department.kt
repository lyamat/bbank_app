package com.example.bbank.domain.models

import android.os.Parcelable
import com.example.bbank.data.local.currency_rates.CurrencyRatesEntity
import com.example.bbank.data.local.departments.DepartmentEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Department(
    val usdIn: String,
    val usdOut: String,
    val eurIn: String,
    val eurOut: String,
    val rubIn: String,
    val rubOut: String,
    val gbpIn: String,
    val gbpOut: String,
    val cadIn: String,
    val cadOut: String,
    val plnIn: String,
    val plnOut: String,
    val sekIn: String,
    val sekOut: String,
    val chfIn: String,
    val chfOut: String,
    val usdEurIn: String,
    val usdEurOut: String,
    val usdRubIn: String,
    val usdRubOut: String,
    val rubEurIn: String,
    val rubEurOut: String,
    val jpyIn: String,
    val jpyOut: String,
    val cnyIn: String,
    val cnyOut: String,
    val cnyEurIn: String,
    val cnyEurOut: String,
    val cnyUsdIn: String,
    val cnyUsdOut: String,
    val cnyRubIn: String,
    val cnyRubOut: String,
    val czkIn: String,
    val czkOut: String,
    val nokIn: String,
    val nokOut: String,
    val filialId: String,
    val sapId: String,
    val infoWorktime: String,
    val streetType: String,
    val street: String,
    val filialsText: String,
    val homeNumber: String,
    val name: String,
    val nameType: String
) : Parcelable

internal fun Department.toDepartmentEntity() =
    DepartmentEntity(
        usdIn = usdIn,
        usdOut = usdOut,
        eurIn = eurIn,
        eurOut = eurOut,
        rubIn = rubIn,
        rubOut = rubOut,
        gbpIn = gbpIn,
        gbpOut = gbpOut,
        cadIn = cadIn,
        cadOut = cadOut,
        plnIn = plnIn,
        plnOut = plnOut,
        sekIn = sekIn,
        sekOut = sekOut,
        chfIn = chfIn,
        chfOut = chfOut,
        usdEurIn = usdEurIn,
        usdEurOut = usdEurOut,
        usdRubIn = usdRubIn,
        usdRubOut = usdRubOut,
        rubEurIn = rubEurIn,
        rubEurOut = rubEurOut,
        jpyIn = jpyIn,
        jpyOut = jpyOut,
        cnyIn = cnyIn,
        cnyOut = cnyOut,
        cnyEurIn = cnyEurIn,
        cnyEurOut = cnyEurOut,
        cnyUsdIn = cnyUsdIn,
        cnyUsdOut = cnyUsdOut,
        cnyRubIn = cnyRubIn,
        cnyRubOut = cnyRubOut,
        czkIn = czkIn,
        czkOut = czkOut,
        nokIn = nokIn,
        nokOut = nokOut,
        filialId = filialId,
        sapId = sapId,
        infoWorktime = infoWorktime,
        streetType = streetType,
        street = street,
        filialsText = filialsText,
        homeNumber = homeNumber,
        name = name,
        nameType = nameType
    )

internal fun Department.toCurrencyRatesEntity() =
    CurrencyRatesEntity(
        usdIn = usdIn,
        usdOut = usdOut,
        eurIn = eurIn,
        eurOut = eurOut,
        rubIn = rubIn,
        rubOut = rubOut,
        gbpIn = gbpIn,
        gbpOut = gbpOut,
        cadIn = cadIn,
        cadOut = cadOut,
        plnIn = plnIn,
        plnOut = plnOut,
        sekIn = sekIn,
        sekOut = sekOut,
        chfIn = chfIn,
        chfOut = chfOut,
        usdEurIn = usdEurIn,
        usdEurOut = usdEurOut,
        usdRubIn = usdRubIn,
        usdRubOut = usdRubOut,
        rubEurIn = rubEurIn,
        rubEurOut = rubEurOut,
        jpyIn = jpyIn,
        jpyOut = jpyOut,
        cnyIn = cnyIn,
        cnyOut = cnyOut,
        cnyEurIn = cnyEurIn,
        cnyEurOut = cnyEurOut,
        cnyUsdIn = cnyUsdIn,
        cnyUsdOut = cnyUsdOut,
        cnyRubIn = cnyRubIn,
        cnyRubOut = cnyRubOut,
        czkIn = czkIn,
        czkOut = czkOut,
        nokIn = nokIn,
        nokOut = nokOut
    )

internal fun Department.toCurrencyRatesToByn() = listOf(
    Pair("USD", Pair(usdIn, usdOut)),
    Pair("EUR", Pair(eurIn, eurOut)),
    Pair("RUB", Pair(rubIn, rubOut)),
    Pair("GBP", Pair(gbpIn, gbpOut)),
    Pair("CAD", Pair(cadIn, cadOut)),
    Pair("PLN", Pair(plnIn, plnOut)),
    Pair("SEK", Pair(sekIn, sekOut)),
    Pair("CHF", Pair(chfIn, chfOut)),
    Pair("JPY", Pair(jpyIn, jpyOut)),
    Pair("CNY", Pair(cnyIn, cnyOut)),
    Pair("CZK", Pair(czkIn, czkOut)),
    Pair("NOK", Pair(nokIn, nokOut))
)