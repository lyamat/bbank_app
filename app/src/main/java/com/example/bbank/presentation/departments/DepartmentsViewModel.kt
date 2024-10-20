package com.example.bbank.presentation.departments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.converter.toCurrencyRates
import com.example.core.domain.converter.ConverterRepository
import com.example.core.domain.department.DepartmentRepository
import com.example.core.domain.util.Result
import com.example.core.presentation.ui.UiText
import com.example.core.presentation.ui.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DepartmentsViewModel @Inject constructor(
    private val departmentRepository: DepartmentRepository,
    private val converterRepository: ConverterRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DepartmentsState())
    val state: StateFlow<DepartmentsState> get() = _state

    init {
        departmentRepository.getDepartments().onEach { departments ->
            if (departments.isEmpty()) {
                _state.update { it.copy(isLoading = true) }
                fetchRemoteDepartments()
            } else {
                _state.update { it.copy(departments = departments, isLoading = false) }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateDepartmentsStateWithError(uiText: UiText) =
        _state.update {
            it.copy(error = uiText)
        }

    internal fun saveCity(cityName: String) =
        viewModelScope.launch {
            try {
//                saveCurrentCityUseCase(cityName)
//                fetchLocalDepartmentsByCity()
            } catch (e: Exception) {
//                updateDepartmentsStateWithError(e)
            }
        }

    internal fun clearDepartmentsError() {
        _state.value = _state.value.copy(error = null)
    }

    fun fetchRemoteDepartments() {
        viewModelScope.launch {
            when (val result = departmentRepository.fetchDepartments()) {
                is Result.Error -> {
                    updateDepartmentsStateWithError(result.error.asUiText())
                }

                is Result.Success -> {
                    departmentRepository.upsertDepartments(result.data)
                    converterRepository.upsertCurrencyRates(result.data.map { department -> department.toCurrencyRates() })
                }
            }
        }
    }
}