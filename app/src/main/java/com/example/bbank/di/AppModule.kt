package com.example.bbank.di

import android.content.Context
import android.content.SharedPreferences
import com.example.bbank.data.repositories.local.SharedPreferencesLocal
import com.example.bbank.data.repositories.local.SharedPreferencesLocalImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideCoroutineScope(): CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @Singleton
    @Provides
    fun provideSharedPreference(
        @ApplicationContext context: Context
    ): SharedPreferences = context.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    // TODO: remove local
    fun provideSharedPreferencesLocal(
        sharedPreferences: SharedPreferences
    ): SharedPreferencesLocal = SharedPreferencesLocalImpl(sharedPreferences)
}