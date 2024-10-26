package com.example.core.domain.util.extentions

import com.example.core.domain.department.Department

fun Department.getAddressAndName(): String {
    return "$nameType $name, $streetType $street, $homeNumber, $filialsText"
}

fun Department.getAddress(): String {
    return "$nameType $name, $streetType $street, $homeNumber"
}

fun Department.getName(): String {
    return filialsText
}

// todo: move to department:domain, also maybe create const enum for day of week
fun getTimeInMinutes(hours: String, minutes: String): Int =
    hours.toInt() * 60 + minutes.toInt()

fun isCurrentTimeInRange(
    currentTimeInMin: Int,
    openTimeInMin: Int,
    closeTimeInMin: Int,
    breakStartTimeInMin: Int? = null,
    breakEndTimeInMin: Int? = null
): Boolean {
    if (currentTimeInMin !in openTimeInMin until closeTimeInMin) return false
    if (breakStartTimeInMin != null && breakEndTimeInMin != null) return currentTimeInMin !in breakStartTimeInMin until breakEndTimeInMin
    return true
}

fun Department.isOpen(currentTimeInMin: Int, dayOfWeek: Int): Boolean {
    return try {
        // TODO: maybe create model like DepartmentAvailability (id = depId, openTimeInMin, closeTimeInMin, breakStartTimeInMin, breakEndTimeInMin)
        val workTimeParts = getWorkTimeForDayOfWeek(dayOfWeek) ?: return false

        isCurrentTimeInRange(
            currentTimeInMin,
            openTimeInMin = getTimeInMinutes(hours = workTimeParts[0], minutes = workTimeParts[1]),
            closeTimeInMin = getTimeInMinutes(hours = workTimeParts[2], minutes = workTimeParts[3]),
            breakStartTimeInMin = workTimeParts.getOrNull(4)?.toIntOrNull(),
            breakEndTimeInMin = workTimeParts.getOrNull(6)?.toIntOrNull()
        )
    } catch (e: Exception) {
        return false
    }
}

fun Department.getWorkTimeForDayOfWeek(dayOfWeek: Int): List<String>? {
    return try {
        val workTimeParts = infoWorktime.split("|")

        workTimeParts[dayOfWeek - 1]
            .replaceFirst("[А-Яа-я]+".toRegex(), "")
            .trim()
            .takeIf { it.isNotEmpty() }?.split(" ")?.filter { it.isNotBlank() }
    } catch (e: Exception) {
        null
    }
}