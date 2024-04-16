package com.example.bbank.presentation.departments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbank.domain.models.Department
import com.example.bbank.domain.use_cases.GetLocalUseCase
import com.example.bbank.domain.use_cases.GetRemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DepartmentsViewModel @Inject constructor(
    private val getRemoteUseCase: GetRemoteUseCase,
    private val getLocalUseCase: GetLocalUseCase
) : ViewModel() {

    private val _departmentsFlow = MutableStateFlow<DepartmentsEvent>(DepartmentsEvent.Unspecified)
    internal fun departmentsFlow(): Flow<DepartmentsEvent> = _departmentsFlow

    internal fun getRemoteDepartmentsByCity(city: String) {
        viewModelScope.launch {
            try {
                eventHolder(DepartmentsEvent.Loading)
                val departments = getRemoteUseCase.getDepartmentsByCity(city)
                getLocalUseCase.deleteAllLocalDepartments()
                getLocalUseCase.saveToLocalDepartments(departments)
                eventHolder(DepartmentsEvent.DepartmentsSuccess(departments))
            } catch (e: Exception) {
                eventHolder(DepartmentsEvent.Error(e.message.toString()))
            }
        }
    }

    internal fun getLocalDepartmentsByCity() {
        viewModelScope.launch {
            try {
                eventHolder(DepartmentsEvent.Loading)
                val cityName = getLocalUseCase.getCurrentCity()
                val departments = getLocalUseCase.getLocalDepartmentsByCity(cityName)
                eventHolder(DepartmentsEvent.DepartmentsSuccess(departments))
                getLocalUseCase.deleteAllCurrencyRates()
                getLocalUseCase.saveToLocalCurrencyRates(departments)
            } catch (e: Exception) {
                eventHolder(DepartmentsEvent.Error(e.message.toString()))
            }
        }
    }

    internal fun getCurrentCity() {
        viewModelScope.launch {
            try {
                eventHolder(DepartmentsEvent.Loading)
                val cityName = getLocalUseCase.getCurrentCity()
                eventHolder(DepartmentsEvent.CitySuccess(cityName))
            } catch (e: Exception) {
                eventHolder(DepartmentsEvent.Error(e.message.toString()))
            }
        }
    }

    private fun eventHolder(event: DepartmentsEvent) {
        viewModelScope.launch {
            _departmentsFlow.emit(event)
        }
    }

    internal sealed class DepartmentsEvent {
        data object Unspecified : DepartmentsEvent()
        data class DepartmentsSuccess(val departments: List<Department>) : DepartmentsEvent()
        data class Error(val message: String) : DepartmentsEvent()
        data class CitySuccess(val cityName: String) : DepartmentsEvent()
        data object Loading : DepartmentsEvent()
    }
}

