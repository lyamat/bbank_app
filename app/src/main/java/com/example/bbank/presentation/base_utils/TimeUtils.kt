package com.example.bbank.presentation.base_utils

internal object TimeUtils {
    internal fun getTimeInMinutes(hours: String, minutes: String): Int =
        hours.toInt() * 60 + minutes.toInt()

    internal fun isTimeInRange(
        currentTime: Int,
        openTime: Int,
        closeTime: Int,
        breakStart: Int? = null,
        breakEnd: Int? = null
    ): Boolean {
        if (currentTime !in openTime until closeTime) return false
        if (breakStart != null && breakEnd != null) return currentTime !in breakStart until breakEnd
        return true
    }
}
