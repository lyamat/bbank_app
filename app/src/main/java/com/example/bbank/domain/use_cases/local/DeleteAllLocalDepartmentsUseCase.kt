package com.example.bbank.domain.use_cases.local

import com.example.bbank.domain.repositories.LocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DeleteAllLocalDepartmentsUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke() =
        localRepository.deleteAllLocalDepartments()
}