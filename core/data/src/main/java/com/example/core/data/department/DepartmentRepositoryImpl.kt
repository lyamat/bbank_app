package com.example.core.data.department

import com.example.core.data.converter.getConversionRates
import com.example.core.data.converter.toCurrencyRates
import com.example.core.domain.converter.LocalConverterDataSource
import com.example.core.domain.department.Department
import com.example.core.domain.department.DepartmentRepository
import com.example.core.domain.department.LocalDepartmentDataSource
import com.example.core.domain.department.RemoteDepartmentDataSource
import com.example.core.domain.util.DataError
import com.example.core.domain.util.EmptyResult
import com.example.core.domain.util.Result
import com.example.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DepartmentRepositoryImpl @Inject constructor(
    private val localDepartmentDataSource: LocalDepartmentDataSource,
    private val remoteDepartmentDataSource: RemoteDepartmentDataSource,
    private val localConverterDataSource: LocalConverterDataSource,
    private val applicationScope: CoroutineScope
) : DepartmentRepository {
    override fun getDepartments(): Flow<List<Department>> {
        return localDepartmentDataSource.getDepartments()
    }

    override fun getDepartmentById(id: String): Flow<Department> {
        return localDepartmentDataSource.getDepartmentById(id)
    }

    override fun getDepartmentsByCity(cityName: String): Flow<List<Department>> {
        return localDepartmentDataSource.getDepartmentsByCity(cityName)
    }

    override suspend fun upsertDepartments(departments: List<Department>) {
        localDepartmentDataSource.upsertDepartments(departments)
    }

    override suspend fun deleteAllDepartments() {
        localDepartmentDataSource.deleteAllDepartments()
    }

    override suspend fun fetchDepartments(): EmptyResult<DataError> {
        return when (val result = remoteDepartmentDataSource.getDepartments()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localDepartmentDataSource.upsertDepartments(result.data)
                    val currencyRates = result.data.map { it.toCurrencyRates() }
                    localConverterDataSource.upsertCurrencyRates(currencyRates)
                    val currencyRatesOfAirport =
                        currencyRates.first { it.id == "1341" } // most representable of all
                    val conversionRates = currencyRatesOfAirport.getConversionRates()
                    localConverterDataSource.upsertConversionRates(conversionRates).asEmptyDataResult()
                }.await()
            }
        }
    }
}
