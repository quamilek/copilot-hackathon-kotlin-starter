package com.example.currencyexchange.db

import com.example.currencyexchange.model.Currency

class InMemoryStorage{
    val currencies = mutableListOf<Currency>()
}