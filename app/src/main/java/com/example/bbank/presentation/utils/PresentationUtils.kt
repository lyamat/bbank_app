package com.example.bbank.presentation.utils

import com.example.bbank.domain.models.ConversionRate
import com.example.bbank.domain.models.CurrencyRates
import com.example.core.domain.department.Department
import java.util.Locale

internal object PresentationUtils {

    internal fun Department.toCurrencyRatesToByn() = listOf(
        // TODO: (5) remake logic of third parameter (remove hardcoding, strings instead)
        Triple("USD", Pair(usdIn, usdOut), "1 доллар США"),
        Triple("EUR", Pair(eurIn, eurOut), "1 евро"),
        Triple("RUB", Pair(rubIn, rubOut), "100 российских рублей"),
        Triple("GBP", Pair(gbpIn, gbpOut), "1 фунт стерлингов"),
        Triple("CAD", Pair(cadIn, cadOut), "1 канадский доллар"),
        Triple("PLN", Pair(plnIn, plnOut), "10 польских злотых"),
        Triple("SEK", Pair(sekIn, sekOut), "10 шведских крон"),
        Triple("CHF", Pair(chfIn, chfOut), "1 швейцарский франк"),
        Triple("JPY", Pair(jpyIn, jpyOut), "100 японских иен"),
        Triple("CNY", Pair(cnyIn, cnyOut), "10 китайских юаней"),
        Triple("CZK", Pair(czkIn, czkOut), "100 чешских крон"),
        Triple("NOK", Pair(nokIn, nokOut), "10 норвежских крон")
    )

    internal fun Department.getFullAddress(): String {
        return "$nameType $name, $streetType $street, $homeNumber, $filialsText"
    }

    // TODO: (3) maybe use another place/layer for extension func(s)..
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

    private enum class CurrencyUnit(val unit: Int) {
        USD(1),
        EUR(1),
        RUB(100),
        GBP(1),
        CAD(1),
        PLN(10),
        SEK(10),
        CHF(1),
        JPY(100),
        CNY(10),
        CZK(100),
        NOK(10)
    }
}