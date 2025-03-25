package com.example.currencylistapp.domain

import com.example.currencylistapp.data.CurrencyRepository
import com.example.currencylistapp.data.Valute

class CurrencyInteractorImpl(private val currencyRepository: CurrencyRepository) : CurrencyInteractor {
    override suspend fun getCurrencies(): List<Valute> {
        return currencyRepository.getCurrencies()
    }

    override suspend fun updateCurrency(id: String): Valute? {
        return currencyRepository.getCurrency(id)
    }
}