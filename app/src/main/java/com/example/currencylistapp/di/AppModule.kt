package com.example.currencylistapp.di

import android.app.Application
import android.content.Context
import com.example.currencylistapp.data.CurrencyRepository
import com.example.currencylistapp.data.CurrencyRepositoryImpl
import com.example.currencylistapp.data.NetClient
import com.example.currencylistapp.data.RetrofitNetClient
import com.example.currencylistapp.domain.CurrencyInteractor
import com.example.currencylistapp.domain.CurrencyInteractorImpl
import com.example.currencylistapp.presentation.MainViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Application {
        return application
    }

    @Provides
    @Singleton
    fun provideNetClient(): NetClient {
        return RetrofitNetClient()
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(netClient: NetClient): CurrencyRepository {
        return CurrencyRepositoryImpl(netClient)
    }

    @Provides
    @Singleton
    fun provideMainViewModelFactory(currencyInteractor: CurrencyInteractor): MainViewModelFactory {
        return MainViewModelFactory(currencyInteractor)
    }

    @Provides
    @Singleton
    fun provideCurrencyInteractor(currencyRepository: CurrencyRepository): CurrencyInteractor {
        return CurrencyInteractorImpl(currencyRepository)
    }

}