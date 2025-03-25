package com.example.currencylistapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencylistapp.data.Valute
import com.example.currencylistapp.domain.CurrencyInteractor
import kotlinx.coroutines.launch

class MainViewModel(private val currencyInteractor: CurrencyInteractor) : ViewModel() {

    private val _currencies = MutableLiveData<List<Valute>>()
    val currencies: LiveData<List<Valute>> = _currencies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadCurrencies()
    }

    fun loadCurrencies() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _currencies.value = currencyInteractor.getCurrencies()
            } catch (e: Exception) {
                println(e.message)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateCurrency(id: String) {
        viewModelScope.launch {
            try {
                currencyInteractor.updateCurrency(id)?.let { updatedCurrency ->

                    val currentList = _currencies.value?.toMutableList() ?: mutableListOf()
                    val index = currentList.indexOfFirst { it.id == id }
                    if (index != -1) {
                        currentList[index] = updatedCurrency
                        _currencies.value = currentList
                        Log.e("Currency", updatedCurrency.toString())
                    }
                }
            } catch (e: Exception) {

                println(e.message)
            }
        }
    }
}