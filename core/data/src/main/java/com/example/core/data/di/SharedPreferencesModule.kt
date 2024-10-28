package com.example.core.data.di

import android.content.Context
import android.content.SharedPreferences
import com.example.core.data.shared_preferences.SharedPreferencesLocalImpl
import com.example.core.domain.shared_preferences.SharedPreferencesLocal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences = context.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideSharedPreferencesLocal(
        sharedPreferences: SharedPreferences
    ): SharedPreferencesLocal = SharedPreferencesLocalImpl(sharedPreferences)
}
