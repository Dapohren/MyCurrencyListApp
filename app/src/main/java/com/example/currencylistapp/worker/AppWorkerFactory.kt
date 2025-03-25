package com.example.currencylistapp.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.currencylistapp.domain.CurrencyInteractor
import javax.inject.Inject

class AppWorkerFactory @Inject constructor(
    private val currencyInteractor: CurrencyInteractor
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            CurrencyUpdateWorker::class.java.name ->
                CurrencyUpdateWorker(appContext, workerParameters, currencyInteractor)
            else ->
                null
        }
    }
}