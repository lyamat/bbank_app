package com.example.core.data.di

import com.example.core.data.converter.ConverterRepositoryImpl
import com.example.core.data.department.DepartmentRepositoryImpl
import com.example.core.data.news.NewsRepositoryImpl
import com.example.core.domain.converter.ConverterRepository
import com.example.core.domain.department.DepartmentRepository
import com.example.core.domain.news.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository

    @Binds
    @Singleton
    abstract fun bindDepartmentRepository(
        departmentRepositoryImpl: DepartmentRepositoryImpl
    ): DepartmentRepository

    @Binds
    @Singleton
    abstract fun bindConverterRepository(
        converterRepositoryImpl: ConverterRepositoryImpl
    ): ConverterRepository
}
