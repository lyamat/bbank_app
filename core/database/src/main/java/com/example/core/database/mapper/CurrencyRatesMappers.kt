package com.example.core.database.mapper

import com.example.core.database.entity.CurrencyRatesEntity
import com.example.core.domain.converter.CurrencyRates

fun CurrencyRates.toCurrencyRatesEntity(): CurrencyRatesEntity {
    return CurrencyRatesEntity(
        id = id,
        usdIn = usdIn,
        usdOut = usdOut,
        eurIn = eurIn,
        eurOut = eurOut,
        rubIn = rubIn,
        rubOut = rubOut,
        gbpIn = gbpIn,
        gbpOut = gbpOut,
        cadIn = cadIn,
        cadOut = cadOut,
        plnIn = plnIn,
        plnOut = plnOut,
        sekIn = sekIn,
        sekOut = sekOut,
        chfIn = chfIn,
        chfOut = chfOut,
        jpyIn = jpyIn,
        jpyOut = jpyOut,
        cnyIn = cnyIn,
        cnyOut = cnyOut,
        czkIn = czkIn,
        czkOut = czkOut,
        nokIn = nokIn,
        nokOut = nokOut,
        bynIn = bynIn,
        bynOut = bynOut
    )
}

fun CurrencyRatesEntity.toCurrencyRates(): CurrencyRates {
    return CurrencyRates(
        id = id,
        usdIn = usdIn,
        usdOut = usdOut,
        eurIn = eurIn,
        eurOut = eurOut,
        rubIn = rubIn,
        rubOut = rubOut,
        gbpIn = gbpIn,
        gbpOut = gbpOut,
        cadIn = cadIn,
        cadOut = cadOut,
        plnIn = plnIn,
        plnOut = plnOut,
        sekIn = sekIn,
        sekOut = sekOut,
        chfIn = chfIn,
        chfOut = chfOut,
        jpyIn = jpyIn,
        jpyOut = jpyOut,
        cnyIn = cnyIn,
        cnyOut = cnyOut,
        czkIn = czkIn,
        czkOut = czkOut,
        nokIn = nokIn,
        nokOut = nokOut,
        bynIn = bynIn,
        bynOut = bynOut
    )
}