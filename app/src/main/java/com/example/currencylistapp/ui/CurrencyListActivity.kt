package com.example.currencylistapp.ui
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencylistapp.App
import com.example.currencylistapp.R
import com.example.currencylistapp.presentation.MainViewModel
import com.example.currencylistapp.presentation.MainViewModelFactory
import com.example.currencylistapp.ui.CurrencyAdapter
import javax.inject.Inject

class CurrencyListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var refreshButton: Button
    private lateinit var currencyAdapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_list)

        // Инициализация ViewModel ДО нахождения View элементов
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        refreshButton = findViewById(R.id.refreshButton)

        recyclerView.layoutManager = LinearLayoutManager(this)
        currencyAdapter = CurrencyAdapter(viewModel)
        recyclerView.adapter = currencyAdapter

        viewModel.currencies.observe(this, Observer { currencies ->
            currencyAdapter.submitList(currencies)
        })

        viewModel.isLoading.observe(this, Observer { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
        })

        refreshButton.setOnClickListener {
            viewModel.loadCurrencies()
        }

    }
}