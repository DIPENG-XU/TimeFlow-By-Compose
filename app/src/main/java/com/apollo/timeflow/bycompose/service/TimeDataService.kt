package com.apollo.timeflow.bycompose.service

import com.apollo.timeflow.bycompose.R
import com.apollo.timeflow.bycompose.TimeFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class TimeDataService @Inject constructor() {
    fun getCurrentTime(timeFormat: TimeFormat): List<Int> {
        val calendar: Calendar = Calendar.getInstance()
        val timeList = ArrayList<Int>().apply {
            if (timeFormat == TimeFormat.Base24) {
                add(0, calendar.get(Calendar.HOUR_OF_DAY))
            } else {
                if (calendar.get(Calendar.HOUR_OF_DAY) == 12) add(0, 12)
                else add(0, calendar.get(Calendar.HOUR))
            }
            add(1, calendar.get(Calendar.MINUTE))
        }
        return timeList
    }

    fun amOrPm(): Int {
        val calendar: Calendar = Calendar.getInstance()
        return if (calendar.get(Calendar.HOUR_OF_DAY) > 12) R.string.pm else R.string.am
    }

    fun getCurrentDate(): String {
        val date = Date()
        val simpleDateFormat = SimpleDateFormat("MM.dd.yyyy", Locale.CHINA)
        return simpleDateFormat.format(date)
    }
}