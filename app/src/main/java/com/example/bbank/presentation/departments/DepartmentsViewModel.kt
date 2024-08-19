package com.example.bbank.presentation.departments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbank.domain.models.Department
import com.example.bbank.domain.use_cases.local.DeleteAllCurrencyRatesUseCase
import com.example.bbank.domain.use_cases.local.DeleteAllLocalDepartmentsUseCase
import com.example.bbank.domain.use_cases.local.GetCurrentCityUseCase
import com.example.bbank.domain.use_cases.local.GetLocalDepartmentsByCityUseCase
import com.example.bbank.domain.use_cases.local.SaveCurrentCityUseCase
import com.example.bbank.domain.use_cases.local.SaveToLocalCurrencyRatesUseCase
import com.example.bbank.domain.use_cases.local.SaveToLocalDepartmentsUseCase
import com.example.bbank.domain.use_cases.remote.GetRemoteDepartmentsByCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DepartmentsViewModel @Inject constructor(
    private val getCurrentCityUseCase: GetCurrentCityUseCase,
    private val getLocalDepartmentsByCityUseCase: GetLocalDepartmentsByCityUseCase,
    private val deleteAllCurrencyRatesUseCase: DeleteAllCurrencyRatesUseCase,
    private val saveToLocalCurrencyRatesUseCase: SaveToLocalCurrencyRatesUseCase,
    private val getRemoteDepartmentsByCityUseCase: GetRemoteDepartmentsByCityUseCase,
    private val deleteAllLocalDepartmentsUseCase: DeleteAllLocalDepartmentsUseCase,
    private val saveToLocalDepartmentsUseCase: SaveToLocalDepartmentsUseCase,
    private val saveCurrentCityUseCase: SaveCurrentCityUseCase
) : ViewModel() {

    private val _departmentsFlow = MutableStateFlow<DepartmentsEvent>(DepartmentsEvent.Unspecified)
    internal fun departmentsFlow(): StateFlow<DepartmentsEvent> = _departmentsFlow
    private val _cityFlow = MutableStateFlow<DepartmentsEvent>(DepartmentsEvent.Unspecified)
    internal fun cityFlow(): StateFlow<DepartmentsEvent> = _cityFlow

    init {
        getLocalDepartmentsByCity()
    }

    internal fun getLocalDepartmentsByCity() {
        viewModelScope.launch {
            try {
                val cityName = getCurrentCityUseCase()
                val departments = getLocalDepartmentsByCityUseCase(cityName)
                eventHolder(DepartmentsEvent.DepartmentsSuccess(departments))
            } catch (e: Exception) {
                eventHolder(DepartmentsEvent.Error(e.message.toString()))
            }
        }
    }

    internal fun getRemoteDepartmentsByCity(city: String) =
        viewModelScope.launch {
            try {
                eventHolder(DepartmentsEvent.Loading)
                val departments = getRemoteDepartmentsByCityUseCase(city)
                deleteAllLocalDepartmentsUseCase()
                saveToLocalDepartmentsUseCase(departments)
                if (departments.isNotEmpty()) {
                    deleteAllCurrencyRatesUseCase()
                    saveToLocalCurrencyRatesUseCase(departments)
                }
                eventHolder(DepartmentsEvent.DepartmentsSuccess(departments))

            } catch (e: Exception) {
                eventHolder(DepartmentsEvent.Error(e.message.toString()))
            }
        }

    internal fun saveCity(cityName: String) =
        viewModelScope.launch {
            try {
                saveCurrentCityUseCase(cityName)
                eventHolder(DepartmentsEvent.CitySuccess(cityName))
            } catch (e: Exception) {
                eventHolder(DepartmentsEvent.Error(e.message.toString()))
            }
        }

    private fun eventHolder(event: DepartmentsEvent) =
        viewModelScope.launch {
            if (event is DepartmentsEvent.CitySuccess) {
                _cityFlow.emit(event)
            } else _departmentsFlow.emit((event))
        }

    internal sealed class DepartmentsEvent {
        data object Unspecified : DepartmentsEvent()
        data class DepartmentsSuccess(val departments: List<Department>) : DepartmentsEvent()
        data class Error(val message: String) : DepartmentsEvent()
        data class CitySuccess(val cityName: String) : DepartmentsEvent()
        data object Loading : DepartmentsEvent()
    }
}