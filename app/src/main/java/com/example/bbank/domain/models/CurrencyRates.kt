package com.example.bbank.domain.models

import java.util.Locale

internal data class CurrencyRates(
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
    val nokOut: String
)

internal fun CurrencyRates.getConversionRates(): List<ConversionRate> {
    val rates = mutableListOf<ConversionRate>()
    val currencies = CurrencyUnit.entries.map { it.name.lowercase(Locale.ROOT) }
    val currencyValues = mapOf(
        "usd" to Pair(
            usdIn.toDouble() / CurrencyUnit.USD.unit,
            usdOut.toDouble() / CurrencyUnit.USD.unit
        ),
        "eur" to Pair(
            eurIn.toDouble() / CurrencyUnit.EUR.unit,
            eurOut.toDouble() / CurrencyUnit.EUR.unit
        ),
        "rub" to Pair(
            rubIn.toDouble() / CurrencyUnit.RUB.unit,
            rubOut.toDouble() / CurrencyUnit.RUB.unit
        ),
        "gbp" to Pair(
            gbpIn.toDouble() / CurrencyUnit.GBP.unit,
            gbpOut.toDouble() / CurrencyUnit.GBP.unit
        ),
        "cad" to Pair(
            cadIn.toDouble() / CurrencyUnit.CAD.unit,
            cadOut.toDouble() / CurrencyUnit.CAD.unit
        ),
        "pln" to Pair(
            plnIn.toDouble() / CurrencyUnit.PLN.unit,
            plnOut.toDouble() / CurrencyUnit.PLN.unit
        ),
        "sek" to Pair(
            sekIn.toDouble() / CurrencyUnit.SEK.unit,
            sekOut.toDouble() / CurrencyUnit.SEK.unit
        ),
        "chf" to Pair(
            chfIn.toDouble() / CurrencyUnit.CHF.unit,
            chfOut.toDouble() / CurrencyUnit.CHF.unit
        ),
        "jpy" to Pair(
            jpyIn.toDouble() / CurrencyUnit.JPY.unit,
            jpyOut.toDouble() / CurrencyUnit.JPY.unit
        ),
        "cny" to Pair(
            cnyIn.toDouble() / CurrencyUnit.CNY.unit,
            cnyOut.toDouble() / CurrencyUnit.CNY.unit
        ),
        "czk" to Pair(
            czkIn.toDouble() / CurrencyUnit.CZK.unit,
            czkOut.toDouble() / CurrencyUnit.CZK.unit
        ),
        "nok" to Pair(
            nokIn.toDouble() / CurrencyUnit.NOK.unit,
            nokOut.toDouble() / CurrencyUnit.NOK.unit
        )
    )

    for (from in currencies) {
        for (to in currencies) {
            if (from != to && from != "byn") {
                val rateIn = 1 / currencyValues[to]!!.first * currencyValues[from]!!.first
                val rateOut = 1 / currencyValues[to]!!.second * currencyValues[from]!!.second
                rates.add(ConversionRate(from + "_in", to + "_in", rateIn))
                rates.add(ConversionRate(from + "_out", to + "_out", rateOut))
            }
        }
    }

    for (currency in currencies) {
        if (currency != "byn") {
            // squared because it has already been divided in currencyValues ( no desire to rewrite :) )
            val rateIn = 1 / (currencyValues[currency]!!.first *
                    CurrencyUnit.valueOf(currency.uppercase(Locale.ROOT)).unit) *
                    CurrencyUnit.valueOf(currency.uppercase(Locale.ROOT)).unit
            val rateOut = 1 / (currencyValues[currency]!!.second *
                    CurrencyUnit.valueOf(currency.uppercase(Locale.ROOT)).unit) *
                    CurrencyUnit.valueOf(currency.uppercase(Locale.ROOT)).unit
            rates.add(ConversionRate("byn_in", currency + "_in", rateIn))
            rates.add(ConversionRate("byn_out", currency + "_out", rateOut))
            rates.add(ConversionRate(currency + "_in", "byn_in", 1 / rateIn))
            rates.add(ConversionRate(currency + "_out", "byn_out", 1 / rateOut))
        }
    }

    return rates
}