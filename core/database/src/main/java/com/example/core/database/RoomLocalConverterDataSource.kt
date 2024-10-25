package com.example.core.database

import android.database.sqlite.SQLiteFullException
import com.example.core.database.dao.ConversionRateDao
import com.example.core.database.dao.CurrencyRatesDao
import com.example.core.database.mapper.toConversionRate
import com.example.core.database.mapper.toConversionRateEntity
import com.example.core.database.mapper.toCurrencyRates
import com.example.core.database.mapper.toCurrencyRatesEntity
import com.example.core.domain.converter.ConversionRate
import com.example.core.domain.converter.ConversionRatesId
import com.example.core.domain.converter.CurrencyRatesId
import com.example.core.domain.converter.DepartmentCurrencyRates
import com.example.core.domain.converter.LocalConverterDataSource
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalConverterDataSource(
    private val currencyRatesDao: CurrencyRatesDao,
    private val conversionRateDao: ConversionRateDao
) : LocalConverterDataSource {
    override suspend fun upsertCurrencyRates(departmentCurrencyRates: List<DepartmentCurrencyRates>): Result<List<CurrencyRatesId>, DataError.Local> {
        return try {
            val entities = departmentCurrencyRates.map { it.toCurrencyRatesEntity() }
            currencyRatesDao.upsertCurrencyRates(entities)
            Result.Success(entities.map { it.id })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }

    }

    override fun getCurrencyRates(): Flow<List<DepartmentCurrencyRates>> {
        return currencyRatesDao.getCurrencyRates()
            .map { currencyRatesEntities ->
                currencyRatesEntities.map { it.toCurrencyRates() }
            }
    }

    override suspend fun upsertConversionRates(conversionRates: List<ConversionRate>): Result<List<ConversionRatesId>, DataError.Local> {
        return try {
            val entities = conversionRates.map { it.toConversionRateEntity() }
            conversionRateDao.upsertConversionRates(entities)
            Result.Success(entities.map { it.id })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override fun getConversionRates(): Flow<List<ConversionRate>> {
        return conversionRateDao.getConversionRates()
            .map { conversionRatesEntities ->
                conversionRatesEntities.map { it.toConversionRate() }
            }
    }
}