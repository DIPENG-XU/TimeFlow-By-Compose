package com.apollo.timeflow.module.homefeed.service.feature

import kotlinx.coroutines.flow.Flow

interface ITimeFormatRecordDataStoreService {
    /**
     * true means date display otherwise not
     */
    val dateFlow: Flow<Boolean>

    /**
     * true means Base 12 otherwise Base 24
     */
    val timeFormatFlow: Flow<Boolean>

    suspend fun updateDateRecord(isDateDisplay: Boolean)

    suspend fun updateTimeFormat(value: Boolean)
}