package com.example.bbank.data.remote.dto

import com.example.bbank.domain.models.Exchanges
import com.example.bbank.domain.models.News
import com.google.gson.annotations.SerializedName

data class ExchangesResponseDto(
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
    @SerializedName("filial_id")
    val filialId: String?,
    @SerializedName("street_type")
    val streetType: String?,
    @SerializedName("street")
    val street: String?,
    @SerializedName("filials_text")
    val filialsText: String?,
    @SerializedName("home_number")
    val homeNumber: String?
)
{
    companion object {
        fun empty(): ExchangesResponseDto =
            ExchangesResponseDto(
                usdIn = "",
                usdOut = "",
                eurIn = "",
                eurOut = "",
                rubIn = "",
                rubOut = "",
                filialId = "",
                streetType = "",
                street = "",
                filialsText = "",
                homeNumber = ""
            )
    }
}

fun ExchangesResponseDto.toExchanges() =
    Exchanges(
        usdIn = usdIn ?: "",
        usdOut = usdOut ?: "",
        eurIn = eurIn ?: "",
        eurOut = eurOut ?: "",
        rubIn = rubIn ?: "",
        rubOut = rubOut ?: "",
        filialId = filialId ?: "",
        streetType = streetType ?: "",
        street = street ?: "",
        filialsText = filialsText ?: "",
        homeNumber = homeNumber ?: ""
    )

