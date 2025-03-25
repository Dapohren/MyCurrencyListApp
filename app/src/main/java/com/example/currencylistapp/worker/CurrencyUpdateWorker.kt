package com.example.currencylistapp.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.currencylistapp.App
import com.example.currencylistapp.data.CurrencyRepository
import com.example.currencylistapp.domain.CurrencyInteractor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

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
                 ExistingPeriodicWorkPolicy.REPLACE, // Или KEEP, в зависимости от нужного поведения
                 workRequest
             )
        }

        fun cancelWork(context: Context) {
              WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
        }
    }
}