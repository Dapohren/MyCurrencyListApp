package com.example.currencylistapp.data

interface CurrencyRepository {
    suspend fun getCurrencies(): List<Valute>
    suspend fun getCurrency(id: String): Valute?
}
