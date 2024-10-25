package com.example.core.domain.news

// TODO: ttodo
interface NotificationService<T> {
    fun showNotification(data: T)
}