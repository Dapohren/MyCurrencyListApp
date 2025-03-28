package com.example.currencylistapp.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
class RetrofitNetClient : NetClient {

    private val baseUrl = "https://cbr.ru/scripts/"

    private val retrofit: Retrofit by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(httpClient)
            .build()
    }

    override val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}