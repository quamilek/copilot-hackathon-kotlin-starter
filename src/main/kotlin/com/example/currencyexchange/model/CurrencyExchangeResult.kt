package com.example.currencyexchange.model

import java.math.BigDecimal

data class CurrencyExchangeResult(
    val currency:  String,
    val value: BigDecimal,
    val date: String,
)