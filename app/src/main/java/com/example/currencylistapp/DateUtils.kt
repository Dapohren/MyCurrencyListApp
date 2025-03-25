package com.example.currencylistapp

// DateUtils.kt
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getCurrentDate(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(calendar.time)
}