package com.example.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DepartmentEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val usdIn: String,
    val usdOut: String,
    val eurIn: String,
    val eurOut: String,
    val rubIn: String,
    val rubOut: String,
    val gbpIn: String,
    val gbpOut: String,
    val cadIn: String,
    val cadOut: String,
    val plnIn: String,
    val plnOut: String,
    val sekIn: String,
    val sekOut: String,
    val chfIn: String,
    val chfOut: String,
    val usdEurIn: String,
    val usdEurOut: String,
    val usdRubIn: String,
    val usdRubOut: String,
    val rubEurIn: String,
    val rubEurOut: String,
    val jpyIn: String,
    val jpyOut: String,
    val cnyIn: String,
    val cnyOut: String,
    val cnyEurIn: String,
    val cnyEurOut: String,
    val cnyUsdIn: String,
    val cnyUsdOut: String,
    val cnyRubIn: String,
    val cnyRubOut: String,
    val czkIn: String,
    val czkOut: String,
    val nokIn: String,
    val nokOut: String,
    val filialId: String,
    val sapId: String,
    val infoWorktime: String,
    val streetType: String,
    val street: String,
    val filialsText: String,
    val homeNumber: String,
    val name: String,
    val nameType: String
)