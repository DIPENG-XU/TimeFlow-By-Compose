package com.apollo.timeflow.module.settings.service.feature

import kotlinx.coroutines.flow.Flow

interface IDateFormatService {
    val dateFormatFlow: Flow<String>

    suspend fun updateThemeRecord(dateFormat: String)
}