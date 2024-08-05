package com.apollo.timeflow.module.homefeed.service.feature

import kotlinx.coroutines.flow.Flow

interface IThemeService {
    /**
     * * 0 -> [LIGHT]
     * * -1 -> [LIGHT_PROTECTED]
     * * 1 -> [DARK]
     * * 2 -> [DARK_PROTECTED]
     */
    val themeFlow: Flow<Int>

    suspend fun updateThemeRecord(value: Int)

    companion object {
        const val LIGHT = 0
        const val LIGHT_PROTECTED = -1
        const val DARK = 1
        const val DARK_PROTECTED = 2

        val LIGHT_ALL_SET = setOf(LIGHT, LIGHT_PROTECTED)
        val DARK_ALL_SET = setOf(DARK, DARK_PROTECTED)
    }
}