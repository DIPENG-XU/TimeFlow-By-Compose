package com.apollo.timeflow.module.homefeed.service

import com.apollo.timeflow.R
import com.apollo.timeflow.utils.TimeFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class TimeDataService(
    private val coroutineScope: CoroutineScope,
    private val iFetchTimeData: IFetchTimeData,
) {
    @Inject
    constructor(
        coroutineScope: CoroutineScope
    ) : this(
        coroutineScope,
        FetchTimeData(),
    )

    suspend fun getCurrentTime(timeFormat: TimeFormat): Pair<Int, Int> = withContext(coroutineScope.coroutineContext) {
        val calendar: Calendar = iFetchTimeData.fetchCalendar()
        val hours = when {
            timeFormat == TimeFormat.Base24 -> calendar.get(Calendar.HOUR_OF_DAY)
            (calendar.get(Calendar.HOUR_OF_DAY) == 12) -> 12
            else -> calendar.get(Calendar.HOUR)
        }
        val minutes = calendar.get(Calendar.MINUTE)
        hours to minutes
    }

    suspend fun amOrPm(): Int = withContext(coroutineScope.coroutineContext) {
        val calendar: Calendar = iFetchTimeData.fetchCalendar()
        if (calendar.get(Calendar.HOUR_OF_DAY) > 12) R.string.pm else R.string.am
    }

    suspend fun getCurrentDate(dateFormatPattern: String): String = withContext(coroutineScope.coroutineContext) {
        val date = iFetchTimeData.fetchDate()
        try {
            SimpleDateFormat(dateFormatPattern, Locale.CHINA)
        } catch (e: Exception) {
            e.printStackTrace()
            SimpleDateFormat("MM.dd.yyyy", Locale.CHINA)
        }.format(date)
    }
}

interface IFetchTimeData {
    fun fetchCalendar(): Calendar

    fun fetchDate(): Date
}

class FetchTimeData : IFetchTimeData {
    override fun fetchCalendar(): Calendar = Calendar.getInstance()

    override fun fetchDate(): Date = Date()

}