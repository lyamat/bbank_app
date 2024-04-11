package com.example.bbank.data.remote.dto

import com.example.bbank.domain.models.Department
import com.google.gson.annotations.SerializedName

internal data class DepartmentResponseDto(
    @SerializedName("USD_in")
    val usdIn: String?,
    @SerializedName("USD_out")
    val usdOut: String?,
    @SerializedName("EUR_in")
    val eurIn: String?,
    @SerializedName("EUR_out")
    val eurOut: String?,
    @SerializedName("RUB_in")
    val rubIn: String?,
    @SerializedName("RUB_out")
    val rubOut: String?,
    @SerializedName("GBP_in")
    val gbpIn: String?,
    @SerializedName("GBP_out")
    val gbpOut: String?,
    @SerializedName("CAD_in")
    val cadIn: String?,
    @SerializedName("CAD_out")
    val cadOut: String?,
    @SerializedName("PLN_in")
    val plnIn: String?,
    @SerializedName("PLN_out")
    val plnOut: String?,
    @SerializedName("SEK_in")
    val sekIn: String?,
    @SerializedName("SEK_out")
    val sekOut: String?,
    @SerializedName("CHF_in")
    val chfIn: String?,
    @SerializedName("CHF_out")
    val chfOut: String?,
    @SerializedName("USD_EUR_in")
    val usdEurIn: String?,
    @SerializedName("USD_EUR_out")
    val usdEurOut: String?,
    @SerializedName("USD_RUB_in")
    val usdRubIn: String?,
    @SerializedName("USD_RUB_out")
    val usdRubOut: String?,
    @SerializedName("RUB_EUR_in")
    val rubEurIn: String?,
    @SerializedName("RUB_EUR_out")
    val rubEurOut: String?,
    @SerializedName("JPY_in")
    val jpyIn: String?,
    @SerializedName("JPY_out")
    val jpyOut: String?,
    @SerializedName("CNY_in")
    val cnyIn: String?,
    @SerializedName("CNY_out")
    val cnyOut: String?,
    @SerializedName("CNY_EUR_in")
    val cnyEurIn: String?,
    @SerializedName("CNY_EUR_out")
    val cnyEurOut: String?,
    @SerializedName("CNY_USD_in")
    val cnyUsdIn: String?,
    @SerializedName("CNY_USD_out")
    val cnyUsdOut: String?,
    @SerializedName("CNY_RUB_in")
    val cnyRubIn: String?,
    @SerializedName("CNY_RUB_out")
    val cnyRubOut: String?,
    @SerializedName("CZK_in")
    val czkIn: String?,
    @SerializedName("CZK_out")
    val czkOut: String?,
    @SerializedName("NOK_in")
    val nokIn: String?,
    @SerializedName("NOK_out")
    val nokOut: String?,
    @SerializedName("filial_id")
    val filialId: String?,
    @SerializedName("sap_id")
    val sapId: String?,
    @SerializedName("info_worktime")
    val infoWorktime: String?,
    @SerializedName("street_type")
    val streetType: String?,
    @SerializedName("street")
    val street: String?,
    @SerializedName("filials_text")
    val filialsText: String?,
    @SerializedName("home_number")
    val homeNumber: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("name_type")
    val nameType: String?
) {
    internal companion object {
        fun empty(): DepartmentResponseDto =
            DepartmentResponseDto(
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
                nokOut = "",
                filialId = "",
                sapId = "",
                infoWorktime = "",
                streetType = "",
                street = "",
                filialsText = "",
                homeNumber = "",
                name = "",
                nameType = ""
            )
    }
}

internal fun DepartmentResponseDto.toDepartment() =
    Department(
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
        nokOut = nokOut ?: "",
        filialId = filialId ?: "",
        sapId = sapId ?: "",
        infoWorktime = infoWorktime ?: "",
        streetType = streetType ?: "",
        street = street ?: "",
        filialsText = filialsText ?: "",
        homeNumber = homeNumber ?: "",
        name = name ?: "",
        nameType = nameType ?: ""
    )