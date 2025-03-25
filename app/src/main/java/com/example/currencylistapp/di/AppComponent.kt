package com.example.currencylistapp.di

import androidx.work.WorkerFactory
import com.example.currencylistapp.App
import com.example.currencylistapp.ui.CurrencyListActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: CurrencyListActivity)
    fun inject(app: App)
    fun workerFactory(): WorkerFactory
}