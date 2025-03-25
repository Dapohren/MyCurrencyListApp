package com.example.currencylistapp.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.currencylistapp.domain.CurrencyInteractor
import java.util.concurrent.TimeUnit

class CurrencyUpdateWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val currencyInteractor: CurrencyInteractor
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            currencyInteractor.getCurrencies() // Обновляем данные
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        const val WORK_NAME = "CurrencyUpdateWorker"

        fun startPeriodicWork(context: Context) {
            val workRequest = PeriodicWorkRequestBuilder<CurrencyUpdateWorker>(
                30, TimeUnit.MINUTES
            ).build()

             WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                 ExistingPeriodicWorkPolicy.REPLACE,
                 workRequest
             )
        }

    }
}