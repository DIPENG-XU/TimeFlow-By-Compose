package com.apollo.timeflow.module.homefeed.service.featureImpl

import com.apollo.timeflow.R
import com.apollo.timeflow.module.homefeed.service.dependency.IDateModule
import com.apollo.timeflow.module.homefeed.service.feature.ITimeDataService
import com.apollo.timeflow.utils.TimeFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class TimeDataService @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val iDateModule: IDateModule,
): ITimeDataService {
    override suspend fun getCurrentTime(timeFormat: TimeFormat): Pair<Int, Int> = withContext(coroutineScope.coroutineContext) {
        val calendar: Calendar = iDateModule.fetchCalendar()
        val hours = when {
            timeFormat == TimeFormat.Base24 -> calendar.get(Calendar.HOUR_OF_DAY)
            (calendar.get(Calendar.HOUR_OF_DAY) == 12) -> 12
            else -> calendar.get(Calendar.HOUR)
        }
        val minutes = calendar.get(Calendar.MINUTE)
        hours to minutes
    }

    override suspend fun amOrPm(): Int = withContext(coroutineScope.coroutineContext) {
        val calendar: Calendar = iDateModule.fetchCalendar()
        if (calendar.get(Calendar.HOUR_OF_DAY) > 12) R.string.pm else R.string.am
    }

    override suspend fun getCurrentDate(dateFormatPattern: String): String = withContext(coroutineScope.coroutineContext) {
        val date = iDateModule.fetchDate()
        try {
            SimpleDateFormat(dateFormatPattern, Locale.CHINA)
        } catch (e: Exception) {
            e.printStackTrace()
            SimpleDateFormat("MM.dd.yyyy", Locale.CHINA)
        }.format(date)
    }
}