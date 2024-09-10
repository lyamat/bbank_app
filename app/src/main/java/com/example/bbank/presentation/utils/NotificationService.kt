package com.example.bbank.presentation.utils

internal interface NotificationService<T> {
    fun showNotification(data: T)
}