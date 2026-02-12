package com.apollo.timeflow

import androidx.annotation.FontRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModelStoreOwner
import com.apollo.timeflow.module.settings.utils.FontMappingType

object RootConfig {
    val LocalFontNameConfig = compositionLocalOf<String> {
        FontMappingType.PoppinsBold.name
    }

    @FontRes
    @Composable
    fun getCurrentFontRes(): Int {
        return FontMappingType.getFontMappingTypeByName(LocalFontNameConfig.current).fontRes
    }

    val LocalActivityViewModelStoreOwner = compositionLocalOf<ViewModelStoreOwner> {
        error("It must provide the LocalActivityViewModelStoreOwner from The Host Activity")
    }
}