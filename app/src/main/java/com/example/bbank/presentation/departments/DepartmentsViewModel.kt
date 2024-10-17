package com.example.bbank.presentation.departments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbank.domain.models.Department
import com.example.bbank.domain.usecase.local.DeleteAllCurrencyRatesUseCase
import com.example.bbank.domain.usecase.local.DeleteAllLocalDepartmentsUseCase
import com.example.bbank.domain.usecase.local.GetCurrentCityUseCase
import com.example.bbank.domain.usecase.local.GetLocalDepartmentsByCityUseCase
import com.example.bbank.domain.usecase.local.SaveCurrentCityUseCase
import com.example.bbank.domain.usecase.local.SaveToLocalCurrencyRatesUseCase
import com.example.bbank.domain.usecase.local.SaveToLocalDepartmentsUseCase
import com.example.core.presentation.ui.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DepartmentsViewModel @Inject constructor(
    private val getCurrentCityUseCase: GetCurrentCityUseCase,
    private val getLocalDepartmentsByCityUseCase: GetLocalDepartmentsByCityUseCase,
    private val deleteAllCurrencyRatesUseCase: DeleteAllCurrencyRatesUseCase,
    private val saveToLocalCurrencyRatesUseCase: SaveToLocalCurrencyRatesUseCase,
//    private val getRemoteDepartmentsByCityUseCase: GetRemoteDepartmentsByCityUseCase,
    private val deleteAllLocalDepartmentsUseCase: DeleteAllLocalDepartmentsUseCase,
    private val saveToLocalDepartmentsUseCase: SaveToLocalDepartmentsUseCase,
    private val saveCurrentCityUseCase: SaveCurrentCityUseCase
) : ViewModel() {

    private val _departmentsUiState = MutableStateFlow(DepartmentsUiState())
    val departmentsUiState: StateFlow<DepartmentsUiState> get() = _departmentsUiState

    init {
        fetchLocalDepartmentsByCity()
    }

    private var getRemoteDepartmentsJob: Job? = null

    internal fun fetchRemoteDepartmentsByCity() {
//        if (getRemoteDepartmentsJob != null) return
//        getRemoteDepartmentsJob = viewModelScope.launch {
//            _departmentsUiState.update { it.copy(isLoading = true) }
//            when (val result = getRemoteDepartmentsByCityUseCase()) {
//                is Result.Success -> handleSuccessfulDepartmentsFetch(result.data)
//                is Result.Error -> updateDepartmentsStateWithError(result.error.asUiText())
//            }
//            getRemoteDepartmentsJob = null
//        }
    }

    private suspend fun handleSuccessfulDepartmentsFetch(departments: List<Department>) {
        if (departments.isNotEmpty()) {
            deleteAllLocalDepartmentsUseCase()
            saveToLocalDepartmentsUseCase(departments)
            _departmentsUiState.update { DepartmentsUiState(departments = departments) }
            deleteAllCurrencyRatesUseCase()
            saveToLocalCurrencyRatesUseCase(departments.first())
        }
    }

    private fun fetchLocalDepartmentsByCity() =
        viewModelScope.launch {
            _departmentsUiState.update { it.copy(isLoading = true) }
            try {
                val cityName = getCurrentCityUseCase()
                val departments = getLocalDepartmentsByCityUseCase(cityName)
                _departmentsUiState.update {
                    it.copy(departments = departments, currentCity = cityName, isLoading = false)
                }
            } catch (e: Exception) {
                updateDepartmentsStateWithError(e)
            }
        }

    private fun updateDepartmentsStateWithError(uiText: UiText) =
        _departmentsUiState.update {
            it.copy(
                error = uiText,
                isLoading = false
            )
        }

    private fun updateDepartmentsStateWithError(e: Exception) =
        _departmentsUiState.update {
            it.copy(
                error = e.message?.let { message -> UiText.DynamicString(message) },
                isLoading = false
            )
        }

    internal fun saveCity(cityName: String) =
        viewModelScope.launch {
            try {
                saveCurrentCityUseCase(cityName)
                fetchLocalDepartmentsByCity()
            } catch (e: Exception) {
                updateDepartmentsStateWithError(e)
            }
        }

    internal fun clearDepartmentsError() {
        _departmentsUiState.value = _departmentsUiState.value.copy(error = null)
    }
}