package com.example.currencyexchange

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal


class Storage{
    val currencies = mutableListOf<Currency>()
}



@RestController
class ExampleController {

    val storage = Storage()

    @GetMapping("/health-check")
    fun greeting(): String {
        return "health check OK"
    }

    @PostMapping(value = ["/currency"])
    @ResponseBody
    fun addCurrency(@RequestBody currencies: Currencies): ResponseEntity<Currencies>{
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

data class CurrencyExchangeResult(
    val currency:  String,
    val value: BigDecimal,
    val date: String,
)

data class Exchange(
    val from_currency: String,
    val to_currency: String,
    val amount: BigDecimal,
    val date: String
)


data class Currencies(
    val currencies: List<Currency>
)


data class Currency(
    val currency: String,
    val price_pln: String,
    val date: String,
)