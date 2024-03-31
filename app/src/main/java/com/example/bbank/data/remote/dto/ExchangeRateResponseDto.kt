package com.example.bbank.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ExchangeRateResponseDto(
    @SerializedName("USD_in") val usdIn: String,
    @SerializedName("USD_out") val usdOut: String,
    @SerializedName("EUR_in") val eurIn: String,
    @SerializedName("EUR_out") val eurOut: String,
    @SerializedName("RUB_in") val rubIn: String,
    @SerializedName("RUB_out") val rubOut: String,
    @SerializedName("filial_id") val filialId: String,
    @SerializedName("street_type") val streetType: String,
    @SerializedName("street") val street: String,
    @SerializedName("filials_text") val filialsText: String,
    @SerializedName("home_number") val homeNumber: String
)
