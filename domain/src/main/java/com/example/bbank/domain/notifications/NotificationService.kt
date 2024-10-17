package com.example.bbank.domain.notifications

interface NotificationService<T> {
    fun showNotification(data: T)
}