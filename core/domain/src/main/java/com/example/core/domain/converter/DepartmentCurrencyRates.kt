package com.example.core.domain.converter

data class DepartmentCurrencyRates(
    val id: String,
    val usdIn: Double, val usdOut: Double,
    val eurIn: Double, val eurOut: Double,
    val rubIn: Double, val rubOut: Double,
    val gbpIn: Double, val gbpOut: Double,
    val cadIn: Double, val cadOut: Double,
    val plnIn: Double, val plnOut: Double,
    val sekIn: Double, val sekOut: Double,
    val chfIn: Double, val chfOut: Double,
    val jpyIn: Double, val jpyOut: Double,
    val cnyIn: Double, val cnyOut: Double,
    val czkIn: Double, val czkOut: Double,
    val nokIn: Double, val nokOut: Double,
    val bynIn: Double = 1.0, val bynOut: Double = 1.0
)
