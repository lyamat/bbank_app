package com.example.core.domain.converter

enum class Currency(val scale: Int) {
    BYN(1),
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
    NOK(10);
}