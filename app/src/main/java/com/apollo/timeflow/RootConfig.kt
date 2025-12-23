package com.apollo.timeflow

import androidx.annotation.FontRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import com.apollo.timeflow.module.settings.utils.FontMappingType

object RootConfig {
    val DEFAULT_FONT_NAME = FontMappingType.PoppinsBold.name

    val LocalFontNameConfig = compositionLocalOf<String> {
        DEFAULT_FONT_NAME
    }

    @FontRes
    @Composable
    fun getCurrentFontRes(): Int {
        val currentFontName = LocalFontNameConfig.current
        return try {
            FontMappingType.getFontMappingTypeByName(currentFontName).fontRes
        } catch (_: Exception) {
            FontMappingType.PoppinsBold.fontRes
        }
    }
}