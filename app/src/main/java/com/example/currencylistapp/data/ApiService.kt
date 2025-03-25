package com.example.currencylistapp.data

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("XML_daily.asp")
    suspend fun getCurrencies(@Query("date_req") date: String): ResponseBody
}