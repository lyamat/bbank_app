package com.example.bbank.domain.use_cases.remote

import com.example.bbank.data.remote.dto.toNews
import com.example.bbank.domain.models.News
import com.example.bbank.domain.repositories.RemoteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetRemoteNewsUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(): List<News> =
        remoteRepository.getRemoteNews().map {
            it.toNews()
        }
}