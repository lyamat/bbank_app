package com.example.core.data.converter

import com.example.core.domain.converter.ConversionRate
import com.example.core.domain.converter.Currency
import com.example.core.domain.converter.CurrencyRates

fun CurrencyRates.getRateIn(currency: Currency): Double {
    return when (currency) {
        Currency.USD -> usdIn
        Currency.EUR -> eurIn
        Currency.RUB -> rubIn
        Currency.GBP -> gbpIn
        Currency.CAD -> cadIn
        Currency.PLN -> plnIn
        Currency.SEK -> sekIn
        Currency.CHF -> chfIn
        Currency.JPY -> jpyIn
        Currency.CNY -> cnyIn
        Currency.CZK -> czkIn
        Currency.NOK -> nokIn
        Currency.BYN -> bynIn
    }
}

fun CurrencyRates.getRateOut(currency: Currency): Double {
    return when (currency) {
        Currency.USD -> usdOut
        Currency.EUR -> eurOut
        Currency.RUB -> rubOut
        Currency.GBP -> gbpOut
        Currency.CAD -> cadOut
        Currency.PLN -> plnOut
        Currency.SEK -> sekOut
        Currency.CHF -> chfOut
        Currency.JPY -> jpyOut
        Currency.CNY -> cnyOut
        Currency.CZK -> czkOut
        Currency.NOK -> nokOut
        Currency.BYN -> bynOut
    }
}

fun CurrencyRates.getConversionRates(): List<ConversionRate> {
    val rates = mutableListOf<ConversionRate>()
    val currencies = Currency.getAllCurrencies()

    for (fromCurrency in currencies) {
        for (toCurrency in currencies) {
            if (fromCurrency != toCurrency) {
                val rateIn = calculateRateFromTo(fromCurrency, toCurrency, true)
                val rateOut = calculateRateFromTo(fromCurrency, toCurrency, false)
                rates.add(ConversionRate(fromCurrency, toCurrency, rateIn, rateOut))
            }
        }
    }
    return rates
}

private fun CurrencyRates.calculateRateFromTo(
    fromCurrency: Currency,
    toCurrency: Currency,
    isIn: Boolean
): Double {
    val fromRate = if (isIn) getRateIn(fromCurrency) else getRateOut(fromCurrency)
    val toRate = if (isIn) getRateIn(toCurrency) else getRateOut(toCurrency)
    val fromScale = fromCurrency.scale
    val toScale = toCurrency.scale

    if (fromRate == 0.0 || toRate == 0.0) {
        return 0.0
    }

    return (fromRate / fromScale) * (toScale / toRate)
}

