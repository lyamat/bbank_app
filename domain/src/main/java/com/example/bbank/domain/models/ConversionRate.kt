package com.example.bbank.domain.models

data class ConversionRate(
    val from: String,
    val to: String,
    val rate: Double
)