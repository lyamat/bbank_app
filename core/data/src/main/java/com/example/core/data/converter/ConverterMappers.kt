package com.example.core.data.converter

import com.example.core.domain.converter.CurrencyRates
import com.example.core.domain.department.Department

fun Department.toCurrencyRates(): CurrencyRates {
    return CurrencyRates(
        id = filialId,
        usdIn = usdIn.toDouble(),
        usdOut = usdOut.toDouble(),
        eurIn = eurIn.toDouble(),
        eurOut = eurOut.toDouble(),
        rubIn = rubIn.toDouble(),
        rubOut = rubOut.toDouble(),
        gbpIn = gbpIn.toDouble(),
        gbpOut = gbpOut.toDouble(),
        cadIn = cadIn.toDouble(),
        cadOut = cadOut.toDouble(),
        plnIn = plnIn.toDouble(),
        plnOut = plnOut.toDouble(),
        sekIn = sekIn.toDouble(),
        sekOut = sekOut.toDouble(),
        chfIn = chfIn.toDouble(),
        chfOut = chfOut.toDouble(),
        jpyIn = jpyIn.toDouble(),
        jpyOut = jpyOut.toDouble(),
        cnyIn = cnyIn.toDouble(),
        cnyOut = cnyOut.toDouble(),
        czkIn = czkIn.toDouble(),
        czkOut = czkOut.toDouble(),
        nokIn = nokIn.toDouble(),
        nokOut = nokOut.toDouble()
    )
}