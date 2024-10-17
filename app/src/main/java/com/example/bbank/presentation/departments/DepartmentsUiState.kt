package com.example.bbank.presentation.departments

import com.example.bbank.domain.models.Department
import com.example.core.presentation.ui.UiText

internal data class DepartmentsUiState(
    val departments: List<Department> = emptyList(),
    val currentCity: String = "",
    val isLoading: Boolean = false,
    val error: UiText? = null
)