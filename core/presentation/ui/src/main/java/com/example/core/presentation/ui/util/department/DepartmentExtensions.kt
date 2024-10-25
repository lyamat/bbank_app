package com.example.core.presentation.ui.util.department

import android.content.Context
import com.example.core.domain.converter.Currency
import com.example.core.domain.department.Department
import com.example.core.domain.department.DepartmentCurrency
import com.example.core.presentation.ui.R

fun Department.getDepartmentCurrencies(context: Context): List<DepartmentCurrency> {
    return Currency.entries.mapNotNull { currency ->
        when (currency) {
            Currency.USD -> DepartmentCurrency(
                currency,
                usdIn,
                usdOut,
                context.getString(R.string.one_us_dollar)
            )

            Currency.EUR -> DepartmentCurrency(
                currency,
                eurIn,
                eurOut,
                context.getString(R.string.one_euro)
            )

            Currency.RUB -> DepartmentCurrency(
                currency,
                rubIn,
                rubOut,
                context.getString(R.string.one_hundred_russian_rubles)
            )

            Currency.GBP -> DepartmentCurrency(
                currency,
                gbpIn,
                gbpOut,
                context.getString(R.string.one_pound_sterling)
            )

            Currency.CAD -> DepartmentCurrency(
                currency,
                cadIn,
                cadOut,
                context.getString(R.string.one_canadian_dollar)
            )

            Currency.PLN -> DepartmentCurrency(
                currency,
                plnIn,
                plnOut,
                context.getString(R.string.ten_polish_zloty)
            )

            Currency.SEK -> DepartmentCurrency(
                currency,
                sekIn,
                sekOut,
                context.getString(R.string.ten_swedish_kronor)
            )

            Currency.CHF -> DepartmentCurrency(
                currency,
                chfIn,
                chfOut,
                context.getString(R.string.one_swiss_franc)
            )

            Currency.JPY -> DepartmentCurrency(
                currency,
                jpyIn,
                jpyOut,
                context.getString(R.string.one_hundred_japanese_yen)
            )

            Currency.CNY -> DepartmentCurrency(
                currency,
                cnyIn,
                cnyOut,
                context.getString(R.string.ten_chinese_yuan)
            )

            Currency.CZK -> DepartmentCurrency(
                currency,
                czkIn,
                czkOut,
                context.getString(R.string.one_hundred_czech_krona)
            )

            Currency.NOK -> DepartmentCurrency(
                currency,
                nokIn,
                nokOut,
                context.getString(R.string.ten_norwegian_kroner)
            )

            Currency.BYN -> null
        }
    }
}

fun List<DepartmentCurrency>.filterForAvailable(): List<DepartmentCurrency> {
    return this.filter { it.rateIn != "0.0000" && it.rateOut != "0.0000" }
}
