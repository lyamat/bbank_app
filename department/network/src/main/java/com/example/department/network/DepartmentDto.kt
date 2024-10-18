package com.example.department.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DepartmentDto(
    @SerialName("USD_in")
    val usdIn: String?,
    @SerialName("USD_out")
    val usdOut: String?,
    @SerialName("EUR_in")
    val eurIn: String?,
    @SerialName("EUR_out")
    val eurOut: String?,
    @SerialName("RUB_in")
    val rubIn: String?,
    @SerialName("RUB_out")
    val rubOut: String?,
    @SerialName("GBP_in")
    val gbpIn: String?,
    @SerialName("GBP_out")
    val gbpOut: String?,
    @SerialName("CAD_in")
    val cadIn: String?,
    @SerialName("CAD_out")
    val cadOut: String?,
    @SerialName("PLN_in")
    val plnIn: String?,
    @SerialName("PLN_out")
    val plnOut: String?,
    @SerialName("SEK_in")
    val sekIn: String?,
    @SerialName("SEK_out")
    val sekOut: String?,
    @SerialName("CHF_in")
    val chfIn: String?,
    @SerialName("CHF_out")
    val chfOut: String?,
    @SerialName("USD_EUR_in")
    val usdEurIn: String?,
    @SerialName("USD_EUR_out")
    val usdEurOut: String?,
    @SerialName("USD_RUB_in")
    val usdRubIn: String?,
    @SerialName("USD_RUB_out")
    val usdRubOut: String?,
    @SerialName("RUB_EUR_in")
    val rubEurIn: String?,
    @SerialName("RUB_EUR_out")
    val rubEurOut: String?,
    @SerialName("JPY_in")
    val jpyIn: String?,
    @SerialName("JPY_out")
    val jpyOut: String?,
    @SerialName("CNY_in")
    val cnyIn: String?,
    @SerialName("CNY_out")
    val cnyOut: String?,
    @SerialName("CNY_EUR_in")
    val cnyEurIn: String?,
    @SerialName("CNY_EUR_out")
    val cnyEurOut: String?,
    @SerialName("CNY_USD_in")
    val cnyUsdIn: String?,
    @SerialName("CNY_USD_out")
    val cnyUsdOut: String?,
    @SerialName("CNY_RUB_in")
    val cnyRubIn: String?,
    @SerialName("CNY_RUB_out")
    val cnyRubOut: String?,
    @SerialName("CZK_in")
    val czkIn: String?,
    @SerialName("CZK_out")
    val czkOut: String?,
    @SerialName("NOK_in")
    val nokIn: String?,
    @SerialName("NOK_out")
    val nokOut: String?,
    @SerialName("filial_id")
    val filialId: String?,
    @SerialName("sap_id")
    val sapId: String?,
    @SerialName("info_worktime")
    val infoWorktime: String?,
    @SerialName("street_type")
    val streetType: String?,
    @SerialName("street")
    val street: String?,
    @SerialName("filials_text")
    val filialsText: String?,
    @SerialName("home_number")
    val homeNumber: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("name_type")
    val nameType: String?
)
