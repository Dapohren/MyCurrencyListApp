package com.example.currencylistapp.di

import android.app.Application
import com.example.currencylistapp.App
import com.example.currencylistapp.ui.CurrencyListActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: CurrencyListActivity) // Для CurrencyListActivity
    fun inject(app: App)
}