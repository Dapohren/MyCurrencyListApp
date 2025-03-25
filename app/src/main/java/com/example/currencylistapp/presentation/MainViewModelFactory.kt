package com.example.currencylistapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencylistapp.domain.CurrencyInteractor
import java.security.Provider
import javax.inject.Inject

class MainViewModelFactory(private val currencyInteractor: CurrencyInteractor) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(currencyInteractor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}