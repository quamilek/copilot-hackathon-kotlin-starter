package com.example.currencyexchange

import com.example.currencyexchange.db.InMemoryStorage
import com.example.currencyexchange.model.Currencies
import com.example.currencyexchange.model.CurrencyExchangeResult
import com.example.currencyexchange.model.Exchange
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class ExampleController {

    val storage = InMemoryStorage()

    @GetMapping("/health-check")
    fun greeting(): String {
        return "health check OK"
    }

    @PostMapping(value = ["/currency"])
    @ResponseBody
    fun addCurrency(@RequestBody currencies: Currencies): ResponseEntity<Currencies>{
        // little hack to implement js style sort in array
        currencies.currencies.reversed().forEach { currency ->
            storage.currencies.add(0, currency)
        }
        return ResponseEntity(currencies, HttpStatus.CREATED);
    }
    @GetMapping(value = ["/currency"])
    @ResponseBody
    fun getCurrencies(): ResponseEntity<Currencies>{
        return ResponseEntity(Currencies(currencies = storage.currencies), HttpStatus.CREATED);
    }

    @PostMapping(value = ["/currencyExchange"])
    @ResponseBody
    fun currencyExchange(@RequestBody exchange: Exchange): ResponseEntity<CurrencyExchangeResult> {
        val currentExchange = storage.currencies.first{
            currency ->
            currency.currency == exchange.from_currency &&
            currency.date == exchange.date
        }
        val resultValue = (exchange.amount * currentExchange.price_pln.toBigDecimal()).setScale(14)
        val currencyExchangeResult = CurrencyExchangeResult(
            currency = "PLN",
            value = resultValue,
            date = exchange.date
        )
        return ResponseEntity(currencyExchangeResult, HttpStatus.OK);
    }
}


