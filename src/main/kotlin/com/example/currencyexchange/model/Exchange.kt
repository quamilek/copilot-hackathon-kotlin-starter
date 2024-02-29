package com.example.currencyexchange.model

import java.math.BigDecimal

data class Exchange(
    val from_currency: String,
    val to_currency: String,
    val amount: BigDecimal,
    val date: String
)