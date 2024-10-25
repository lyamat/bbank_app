package com.example.core.domain.department

import com.example.core.domain.converter.Currency

data class DepartmentCurrency(
    val currency: Currency,
    val rateIn: String,
    val rateOut: String,
    val scaleDescription: String
)