package com.example.bbank.di.modules

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class RepositoryModule {
//    @Singleton
//    @Provides
//    internal fun provideDepartmentRepository(
//        departmentLocal: DepartmentLocal,
//        departmentRemote: DepartmentRemote
//    ): DepartmentRepository = DepartmentRepositoryImpl(departmentLocal, departmentRemote)

//    @Singleton
//    @Provides
//    internal fun provideCurrencyRepository(
//        currencyRatesLocal: CurrencyRatesLocal,
//        sharedPreferencesLocal: SharedPreferencesLocal
//    ): CurrencyRepository = CurrencyRepositoryImpl(currencyRatesLocal, sharedPreferencesLocal)
//
//    @Singleton
//    @Provides
//    internal fun provideCityRepository(
//        sharedPreferencesLocal: SharedPreferencesLocal
//    ): CityRepository = CityRepositoryImpl(sharedPreferencesLocal)


//    @Singleton
//    @Provides
//    internal fun provideDepartmentsRemote(
//        belarusBankApi: BelarusBankApi
//    ): DepartmentRemote = DepartmentRemoteImpl(belarusBankApi)
//
//    @Singleton
//    @Provides
//    internal fun provideDepartmentsLocal(
//        departmentDao: DepartmentDao
//    ): DepartmentLocal = DepartmentLocalImpl(departmentDao)

//    @Singleton
//    @Provides
//    internal fun provideCurrencyRatesLocal(
//        currencyRatesDao: CurrencyRatesDao
//    ): CurrencyRatesLocal = CurrencyRatesLocalImpl(currencyRatesDao)
}