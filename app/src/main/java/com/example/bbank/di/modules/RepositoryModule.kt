package com.example.bbank.di.modules

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class RepositoryModule {
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
//    internal fun provideCurrencyRatesLocal(
//        currencyRatesDao: CurrencyRatesDao
//    ): CurrencyRatesLocal = CurrencyRatesLocalImpl(currencyRatesDao)
}