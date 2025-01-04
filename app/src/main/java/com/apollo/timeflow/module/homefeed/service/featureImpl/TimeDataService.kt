package com.apollo.timeflow.module.homefeed.service.featureImpl

import com.apollo.timeflow.R
import com.apollo.timeflow.module.homefeed.service.dependency.IDateModule
import com.apollo.timeflow.module.homefeed.service.feature.ITimeDataService
import com.apollo.timeflow.module.homefeed.uistate.TimeUIState
import com.apollo.timeflow.utils.BASE24
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
) : ITimeDataService {
    override suspend fun getCurrentTime(timeFormat: Int): TimeUIState = withContext(coroutineScope.coroutineContext) {
        val calendar: Calendar = iDateModule.fetchCalendar()
        val hours = when {
            timeFormat == BASE24 -> calendar.get(Calendar.HOUR_OF_DAY)
            (calendar.get(Calendar.HOUR_OF_DAY) == 12) -> 12
            else -> calendar.get(Calendar.HOUR)
        }
        val minutes = calendar.get(Calendar.MINUTE)

        TimeUIState(
            hoursLeft = hours / 10,
            hoursRight = hours % 10,
            minutesLeft = minutes / 10,
            minutesRight = minutes % 10,
            amOrPM = amOrPm()
        )
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