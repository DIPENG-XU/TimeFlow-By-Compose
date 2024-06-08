package com.apollo.timeflow.module.homefeed.service.feature

import com.apollo.timeflow.utils.TimeFormat

interface ITimeDataService {
    suspend fun getCurrentTime(timeFormat: TimeFormat): Pair<Int, Int>

    suspend fun amOrPm(): Int

    suspend fun getCurrentDate(dateFormatPattern: String): String
}