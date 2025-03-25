package com.example.currencylistapp


import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.currencylistapp.di.AppComponent
import com.example.currencylistapp.di.AppModule
import com.example.currencylistapp.di.DaggerAppComponent
import com.example.currencylistapp.ui.CurrencyListActivity
import com.example.currencylistapp.worker.AppWorkerFactory
import com.example.currencylistapp.worker.CurrencyUpdateWorker
import javax.inject.Inject

class App : Application() {

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var workerFactory: AppWorkerFactory

    override fun onCreate() {
        super.onCreate()
            appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)) // Передаем Application в AppModule
            .build()

        val configuration = Configuration.Builder()
            .setWorkerFactory(appComponent.workerFactory()) // Use the provided workerFactory
            .build()
        WorkManager.initialize(this, configuration)
        /*appComponent.inject(this)
        Log.d("Worker factory: %s", workerFactory.toString()) // Log the workerFactory

        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(workerFactory).build())
            //CurrencyUpdateWorker.startPeriodicWork(this)*/
    }

}