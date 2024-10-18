package com.example.bbank.presentation.departments

import com.example.core.domain.department.Department
import com.example.core.presentation.ui.UiText

internal data class DepartmentsState(
    val departments: List<Department> = emptyList(),
    val currentCity: String = "",
    val isLoading: Boolean = false,
    val error: UiText? = null
)