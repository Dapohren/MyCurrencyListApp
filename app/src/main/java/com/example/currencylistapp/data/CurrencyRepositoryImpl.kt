package com.example.currencylistapp.data

import com.example.currencylistapp.getCurrentDate


class CurrencyRepositoryImpl(private val netClient: NetClient) : CurrencyRepository {

    private var cachedCurrencies: List<Valute> = emptyList()
    private var cachedDate: String? = null

    override suspend fun getCurrencies(): List<Valute> {
        val currentDate = getCurrentDate()
        return try {
            val valCurs = netClient.api.getCurrencies()
            cachedCurrencies = valCurs.valutes
            cachedDate = currentDate
            cachedCurrencies
        } catch (e: Exception) {
            // Обработка ошибок (например, возврат из кэша, если дата совпадает)
            if (currentDate == cachedDate) {
                cachedCurrencies //Возвращаем закэшированные данные при ошибке сети для той же даты
            } else {
                emptyList() // Или выбросить исключение, в зависимости от требований
            }
        }
    }

    override suspend fun getCurrency(id: String): Valute? {
        val currentDate = getCurrentDate()

        // Сначала проверяем кэш, если дата и ID совпадают.
        if (currentDate == cachedDate) {
            val cachedCurrency = cachedCurrencies.find { it.id == id }
            if (cachedCurrency != null) {
                return cachedCurrency
            }
        }

        // Если нет в кэше или дата другая - запрашиваем с API.
        return try {
            val valCurs = netClient.api.getCurrencies()
            val currency = valCurs.valutes.find { it.id == id }
            if (currency != null) {
                // Обновляем кэш и возвращаем валюту.
                cachedCurrencies = valCurs.valutes
                cachedDate = currentDate
                currency
            } else {
                null
            }
        } catch (e: Exception) {
            null // Или выбросить исключение, в зависимости от требований
        }
    }
}