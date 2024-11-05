package com.example.core.domain.notifications

import com.example.core.domain.news.News

interface NewsNotificationService {
    suspend fun showNotification(news: News)
    suspend fun createNotificationChannel()
}