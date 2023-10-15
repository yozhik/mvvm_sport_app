package com.example.myapplication.data.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

const val SERVER_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val USER_DATE_PATTERN = "dd.MM.yyyy HH:mm"

fun String.toUnixTimestamp(): Long {
    val simpleDateFormat = SimpleDateFormat(SERVER_DATE_PATTERN, Locale.getDefault())
    val date = simpleDateFormat.parse(this)
    return date?.time ?: 0L
}

fun Long.toFormattedDateString(): String {
    val dateFormat = SimpleDateFormat(USER_DATE_PATTERN, Locale.getDefault())
    val date = Date(this)
    return dateFormat.format(date)
}

class CalendarUtils{
    fun getTodayStartEndTimeInMillis(): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfToday = calendar.timeInMillis

        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfToday = calendar.timeInMillis

        return Pair(startOfToday, endOfToday)
    }
}
