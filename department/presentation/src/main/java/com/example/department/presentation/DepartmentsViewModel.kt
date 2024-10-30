package com.example.department.presentation

import androidx.lifecycle.ViewModel
import com.example.core.domain.department.Department
import com.example.core.domain.department.DepartmentRepository
import com.example.core.domain.shared_preferences.SharedPreferencesLocal
import com.example.core.domain.util.Result
import com.example.core.domain.util.getUniqueCities
import com.example.core.presentation.ui.UiText
import com.example.core.presentation.ui.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
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
    private val sharedPreferencesLocal: SharedPreferencesLocal
) : ViewModel() {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val _state = MutableStateFlow(DepartmentsState())
    val state: StateFlow<DepartmentsState> get() = _state

    init {
        departmentRepository.getDepartments().onEach { departments ->
            if (departments.isEmpty()) {
                fetchDepartments()
            } else {
                val currentCity = sharedPreferencesLocal.getCurrentCity()
                if (currentCity.isNotBlank()) {
                    departmentRepository.getDepartmentsByCity(currentCity)
                        .onEach { departmentsWithCity ->
                            _state.update {
                                DepartmentsState(
                                    departmentsWithCity,
                                    cities = departments.getUniqueCities(),
                                    currentCity = currentCity
                                )
                            }
                        }.launchIn(viewModelScope)
                } else {
                    _state.update {
                        DepartmentsState(
                            departments = departments,
                            cities = departments.getUniqueCities(),
                            currentCity = currentCity
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun fetchDepartments() {
        viewModelScope.launch {
            try {
                setStateIsLoading(true)
                when (val result = departmentRepository.fetchDepartments()) {
                    is Result.Error -> {
                        setStateError(result.error.asUiText())
                    }

                    is Result.Success -> {
                        Unit
                    }
                }
            } catch (e: CancellationException) {
                setIsFetchCanceled(true)
            } finally {
                setStateIsLoading(false)
            }
        }
    }

    fun getDepartmentById(departmentId: String) {
        departmentRepository.getDepartmentById(departmentId).onEach {
            setChosenDepartment(it)
        }.launchIn(viewModelScope)
    }

    private fun setChosenDepartment(departmentResult: Department) =
        _state.update { it.copy(chosenDepartment = departmentResult) }

    fun setIsFetchCanceled(isFetchCanceled: Boolean) =
        _state.update { it.copy(isFetchCanceled = isFetchCanceled) }

    fun setStateError(uiText: UiText?) =
        _state.update { it.copy(error = uiText) }

    private fun setStateIsLoading(isLoading: Boolean) =
        _state.update { it.copy(isLoading = isLoading) }

    fun setCurrentCity(city: String) =
        viewModelScope.launch {
            sharedPreferencesLocal.setCurrentCity(city)
            departmentRepository.getDepartmentsByCity(city).onEach { departments ->
                _state.update { it.copy(departments = departments, currentCity = city) }
            }.launchIn(viewModelScope)
        }

    fun cancelCurrentFetching() {
        viewModelJob.cancelChildren()
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}