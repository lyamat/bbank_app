package com.example.department.presentation

import com.example.core.domain.department.Department
import com.example.core.presentation.ui.UiText

internal data class DepartmentsState(
    val departments: List<Department> = emptyList(),
    val currentCity: String = "",
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val isFetchCanceled: Boolean = false,
    val chosenDepartment: Department? = null,
    val cities: List<String> = emptyList()
)