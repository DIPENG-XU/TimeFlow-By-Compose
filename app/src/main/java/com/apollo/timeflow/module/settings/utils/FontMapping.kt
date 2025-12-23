package com.apollo.timeflow.module.settings.utils

import androidx.annotation.FontRes
import androidx.annotation.StringRes
import com.apollo.timeflow.R


sealed class FontMappingType(
    val name: String,
    @FontRes val fontRes: Int,
    @StringRes val nameRes: Int,
) {
    data object PoppinsBold : FontMappingType(
        "Poppins Bold", R.font.poppins_bold, R.string.font_poppins_bold
    )

    data object RainDay : FontMappingType(
        "Rain day", R.font.rain_day, R.string.font_rain_day
    )

    data object StreetCulture : FontMappingType(
        "Street Culture", R.font.street_culture, R.string.font_street_culture
    )

    data object NightPumpKind : FontMappingType(
        "Night Pump Kind", R.font.night_pump_kind, R.string.font_night_pump_kind
    )

    data object LikeStyle : FontMappingType(
        "Like Style", R.font.like_style, R.string.font_like_style
    )

    data object DearyBook : FontMappingType(
        "Deary Book", R.font.deary_book, R.string.font_deary_book
    )

    companion object {
        fun getFontMappingTypeByName(name: String): FontMappingType {
            return FONT_LIST.find { it -> it.name == name } ?: PoppinsBold
        }
    }
}

val FONT_LIST = listOf(
    FontMappingType.PoppinsBold,
    FontMappingType.RainDay,
    FontMappingType.StreetCulture,
    FontMappingType.NightPumpKind,
    FontMappingType.LikeStyle,
    FontMappingType.DearyBook,
)