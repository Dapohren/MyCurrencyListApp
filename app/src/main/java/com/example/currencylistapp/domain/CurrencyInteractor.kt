package com.example.currencylistapp.domain

import com.example.currencylistapp.data.Valute

interface CurrencyInteractor {
    suspend fun getCurrencies(): List<Valute>
    suspend fun updateCurrency(id: String): Valute?
}