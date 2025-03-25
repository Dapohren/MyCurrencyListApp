package com.example.currencylistapp.data

import android.util.Log
import com.example.currencylistapp.getCurrentDate
import com.example.currencylistapp.parseXml
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody


class CurrencyRepositoryImpl(private val netClient: NetClient) : CurrencyRepository {

    private var cachedCurrencies: List<Valute> = emptyList()
    private var cachedDate: String? = null

    override suspend fun getCurrencies(): List<Valute> {
        val currentDate = getCurrentDate()
        return withContext(Dispatchers.IO) {
            val response: ResponseBody = netClient.api.getCurrencies(currentDate)
            val xmlString = response.string()
            val valCurs = parseXml(xmlString)
            Log.e("CurrencyRepositoryImpl", "getCurrencies: $valCurs")
            valCurs.valutes
        }
        return try {
            cachedCurrencies = getCurrencies()
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
            val valCurs = getCurrencies()
            val currency = valCurs.find { it.id == id }
            if (currency != null) {
                // Обновляем кэш и возвращаем валюту.
                cachedCurrencies = getCurrencies()
                cachedDate = currentDate
                currency
                Log.e("CurrencyRepositoryImpl", "getCurrency: $currency")
            } else {
                null
            }
        } catch (e: Exception) {
            null // Или выбросить исключение, в зависимости от требований
        } as Valute?
    }
}