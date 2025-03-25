package com.example.currencylistapp.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("XML_daily.asp")
    suspend fun getCurrencies(): ValCurs
}