package com.example.bbank.presentation.departments

import androidx.lifecycle.ViewModel
import com.example.core.domain.converter.ConverterRepository
import com.example.core.domain.department.DepartmentRepository
import com.example.core.domain.util.Result
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
    private val converterRepository: ConverterRepository
) : ViewModel() {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main.immediate + viewModelJob)

    private val _state = MutableStateFlow(DepartmentsState())
    val state: StateFlow<DepartmentsState> get() = _state

    init {
        departmentRepository.getDepartments().onEach { departments ->
            if (departments.isEmpty()) {
                fetchDepartments()
            } else {
                _state.update { DepartmentsState(departments) }
            }
        }.launchIn(viewModelScope)
    }

    fun fetchDepartments() {
        viewModelScope.launch {
            try {
                setDepartmentsStateIsLoading(true)
                when (val result = departmentRepository.fetchDepartments()) {
                    is Result.Error -> {
                        setDepartmentsStateError(result.error.asUiText())
                    }

                    is Result.Success -> {
                        Unit
                    }
                }
            } catch (e: CancellationException) {
                setDepartmentsIsFetchCanceled(true)
            } finally {
                setDepartmentsStateIsLoading(false)
            }
        }
    }

    fun setDepartmentsIsFetchCanceled(isFetchCanceled: Boolean) =
        _state.update { it.copy(isFetchCanceled = isFetchCanceled) }

    fun setDepartmentsStateError(uiText: UiText?) =
        _state.update { it.copy(error = uiText) }

    private fun setDepartmentsStateIsLoading(isLoading: Boolean) =
        _state.update { it.copy(isLoading = isLoading) }

    fun saveCity(cityName: String) =
        viewModelScope.launch {
//            saveCurrentCityUseCase(cityName)
//            fetchLocalDepartmentsByCity()
        }

    fun cancelCurrentFetching() {
        viewModelJob.cancelChildren()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}