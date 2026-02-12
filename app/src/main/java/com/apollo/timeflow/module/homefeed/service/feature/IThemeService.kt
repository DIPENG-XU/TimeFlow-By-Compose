package com.apollo.timeflow.module.homefeed.service.feature

import com.apollo.timeflow.module.settings.utils.FONT_LIST
import com.apollo.timeflow.module.settings.utils.FontMappingType
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

    /**
     * Refer the Font Mapping [FontMappingType] and [FONT_LIST]
     */
    val fontFlow: Flow<String>

    suspend fun updateFont(fontName: String)

    companion object {
        const val LIGHT = 0
        const val LIGHT_PROTECTED = -1
        const val DARK = 1
        const val DARK_PROTECTED = 2

        val LIGHT_ALL_SET = setOf(LIGHT, LIGHT_PROTECTED)
        val DARK_ALL_SET = setOf(DARK, DARK_PROTECTED)
    }
}