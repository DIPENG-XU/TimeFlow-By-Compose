package com.apollo.timeflow.bycompose.service

import com.apollo.timeflow.bycompose.R
import com.apollo.timeflow.bycompose.TimeFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class TimeDataService @Inject constructor() {
    suspend fun getCurrentTime(timeFormat: TimeFormat): Pair<Int, Int> =
        withContext(Dispatchers.IO) {
            val calendar: Calendar = Calendar.getInstance()
            val hours = when {
                timeFormat == TimeFormat.Base24 -> calendar.get(Calendar.HOUR_OF_DAY)
                (calendar.get(Calendar.HOUR_OF_DAY) == 12) -> 12
                else -> calendar.get(Calendar.HOUR)
            }
            val minutes = calendar.get(Calendar.MINUTE)
            hours to minutes
        }

    suspend fun amOrPm(): Int = withContext(Dispatchers.IO) {
        val calendar: Calendar = Calendar.getInstance()
        if (calendar.get(Calendar.HOUR_OF_DAY) > 12) R.string.pm else R.string.am
    }

    suspend fun getCurrentDate(): String = withContext(Dispatchers.IO) {
        val date = Date()
        val simpleDateFormat = SimpleDateFormat("MM.dd.yyyy", Locale.CHINA)
        simpleDateFormat.format(date)
    }
}