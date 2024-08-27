package com.example.bbank.domain.use_cases.remote

import com.example.bbank.domain.models.News
import com.example.bbank.domain.repositories.NewsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetRemoteNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) : BaseRemoteUseCase<News, News>(
    repositoryCall = { newsRepository.getRemoteNews() },
    mapper = { it }
)