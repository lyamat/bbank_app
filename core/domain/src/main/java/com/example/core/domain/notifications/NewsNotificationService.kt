package com.example.core.domain.notifications

import com.example.core.domain.news.News

interface NewsNotificationService {
    suspend fun showNewsNotification(news: News)
    suspend fun cancelNewsNotifications()
}