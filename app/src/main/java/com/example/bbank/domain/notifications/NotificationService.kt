package com.example.bbank.domain.notifications

internal interface NotificationService<T> {
    fun showNotification(data: T)
}