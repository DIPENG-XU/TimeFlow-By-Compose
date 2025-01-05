package com.apollo.timeflow.module.homefeed.service.feature

import com.apollo.timeflow.module.homefeed.uistate.TimeUIState

interface ITimeDataService {
    suspend fun getCurrentTime(timeFormat: Int): TimeUIState

    /**
     * @return the Resource from string.xml about am or pm
     */
    suspend fun amOrPm(): Int

    suspend fun getCurrentDate(dateFormatPattern: String): String
}