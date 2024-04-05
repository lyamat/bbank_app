package com.example.bbank.presentation.utils

internal sealed class CriteriaItem {
    data class CityItem(val city: String) : CriteriaItem()
    data class OnWorkItem(var isSelected: Boolean) : CriteriaItem()
}
