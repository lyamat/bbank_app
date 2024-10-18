package com.example.bbank.presentation.departments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.department.DepartmentRepository
import com.example.core.presentation.ui.UiText
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
    private val departmentRepository: DepartmentRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DepartmentsState())
    val state: StateFlow<DepartmentsState> get() = _state

    init {
        viewModelScope.launch {
            departmentRepository.getDepartments().onEach { departments ->
                if (departments.isEmpty()) {
                    _state.update { it.copy(isLoading = true) }
                    viewModelScope.launch {
                        departmentRepository.fetchDepartments()
                    }
                } else {
                    _state.update { it.copy(departments = departments, isLoading = false) }
                }
            }.launchIn(viewModelScope)
        }
    }

//    internal fun fetchRemoteDepartmentsByCity() {
//        if (getRemoteDepartmentsJob != null) return
//        getRemoteDepartmentsJob = viewModelScope.launch {
//            _departmentsUiState.update { it.copy(isLoading = true) }
//            when (val result = getRemoteDepartmentsByCityUseCase()) {
//                is Result.Success -> handleSuccessfulDepartmentsFetch(result.data)
//                is Result.Error -> updateDepartmentsStateWithError(result.error.asUiText())
//            }
//            getRemoteDepartmentsJob = null
//        }
//    }
//
//    private suspend fun handleSuccessfulDepartmentsFetch(departments: List<Department>) {
//        if (departments.isNotEmpty()) {
//            deleteAllLocalDepartmentsUseCase()
//            saveToLocalDepartmentsUseCase(departments)
//            _state.update { DepartmentsState(departments = departments) }
//            deleteAllCurrencyRatesUseCase()
//            saveToLocalCurrencyRatesUseCase(departments.first())
//        }
//    }


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
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            departmentRepository.fetchDepartments()
        }
    }
}