package com.example.core.domain.util.extentions

import com.example.core.domain.department.Department

fun Department.getFullAddress(): String {
    return "$nameType $name, $streetType $street, $homeNumber, $filialsText"
}