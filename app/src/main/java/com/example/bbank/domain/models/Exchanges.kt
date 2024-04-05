package com.example.bbank.domain.models

internal data class Exchanges(
    val usdIn: String,
    val usdOut: String,
    val eurIn: String,
    val eurOut: String,
    val rubIn: String,
    val rubOut: String,
    val filialId: String,
    val streetType: String,
    val street: String,
    val filialsText: String,
    val homeNumber: String
)

