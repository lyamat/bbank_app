//package com.example.bbank.data.repositories
//
//import com.example.bbank.data.local.currency_rates.CurrencyRatesEntity
//import com.example.bbank.data.local.currency_rates.CurrencyRatesEntity.Companion.toCurrencyRates
//import com.example.bbank.data.local.currency_rates.CurrencyRatesEntity.Companion.toCurrencyRatesEntity
//import com.example.bbank.data.repositories.local.CurrencyRatesLocal
//import com.example.bbank.data.repositories.local.SharedPreferencesLocal
//import com.example.bbank.domain.models.CurrencyRates
//import com.example.bbank.domain.models.Department
//import com.example.bbank.domain.repository.CurrencyRepository
//import javax.inject.Inject
//import javax.inject.Singleton
//
//@Singleton
//class CurrencyRepositoryImpl @Inject constructor(
//    private val currencyRatesLocal: CurrencyRatesLocal,
//    private val sharedPreferencesLocal: SharedPreferencesLocal
//) : CurrencyRepository {
//    override suspend fun getLocalCurrencyRates(): List<CurrencyRates> =
//        currencyRatesLocal.getLocalCurrencyRates()?.map { it.toCurrencyRates() }
//            ?: listOf(CurrencyRatesEntity.empty().toCurrencyRates())
//
//    override suspend fun saveToLocalCurrencyRates(department: Department) =
//        currencyRatesLocal.saveToLocalCurrencyRates(department.toCurrencyRatesEntity())
//
//    override suspend fun deleteAllCurrencyRates() =
//        currencyRatesLocal.deleteAllLocalCurrencyRates()
//
//    override suspend fun getLocalCurrencyValues(): List<Pair<String, String>> =
//        sharedPreferencesLocal.getCurrencyValues()
//
//    override suspend fun setLocalCurrencyValues(currencyValues: List<Pair<String, String>>) {
//        sharedPreferencesLocal.setCurrencyValues(currencyValues)
//    }
//}
