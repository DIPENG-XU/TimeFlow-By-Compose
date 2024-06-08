package com.apollo.timeflow.module.homefeed.service.feature

import kotlinx.coroutines.flow.Flow

interface IThemeService {
    /**
     * - 0 -> Light
     * - 1 -> Dark
     */
    val themeFlow: Flow<Int>

    suspend fun updateThemeRecord(value: Int)
}