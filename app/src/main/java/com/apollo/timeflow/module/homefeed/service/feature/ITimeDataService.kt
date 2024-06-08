package com.apollo.timeflow.module.homefeed.service.feature

import com.apollo.timeflow.module.homefeed.uistate.TimeUIState
import com.apollo.timeflow.utils.TimeFormat

interface ITimeDataService {
    suspend fun getCurrentTime(timeFormat: TimeFormat): TimeUIState

    /**
     * @return the Resource from string.xml about am or pm
     */
    suspend fun amOrPm(): Int

    suspend fun getCurrentDate(dateFormatPattern: String): String
}