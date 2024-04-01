package com.example.bbank.domain.use_cases

import com.example.bbank.data.local.toNews
import com.example.bbank.domain.models.News
import com.example.bbank.domain.repositories.LocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLocalNewsUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend fun getLocalNewById(id: Long): News {
        val newsEntity = localRepository.getNewById(id)
        return newsEntity?.toNews() ?: News("Null in $id local news", "", "", "", "")
    }

    suspend fun getLocalNews(): List<News> {
        val newsEntities = localRepository.getLocalNews()
        return newsEntities?.map { newsEntity ->
            newsEntity.toNews()
        }
            ?: listOf(News("Null in local news", "", "", "", ""))
    }

}