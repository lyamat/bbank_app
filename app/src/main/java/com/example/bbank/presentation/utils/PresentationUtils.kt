package com.example.bbank.presentation.utils

import com.example.core.domain.department.Department

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
}