package com.apollo.timeflow.module.settings.utils

import androidx.annotation.FontRes
import androidx.annotation.StringRes
import com.apollo.timeflow.R

// --------------------------- Font Mapping
const val POPPINS_BOLD = 1 shl 10
const val RAIN_DAY = 1 shl 11
const val STREET_CULTURE = 1 shl 12
const val DEARY_BOOK = 1 shl 13
const val LIKE_STYLE = 1 shl 14
const val NIGHT_PUMP_KIND = 1 shl 15

sealed class FontMappingType(
    val name: String,
    @FontRes val fontRes: Int,
    val itemTypeCode: Int,
    @StringRes val nameRes: Int,
) {
    data object PoppinsBold : FontMappingType(
        "Poppins Bold", R.font.poppins_bold, POPPINS_BOLD, R.string.font_poppins_bold
    )

    data object RainDay :
        FontMappingType("Rain day", R.font.rain_day, RAIN_DAY, R.string.font_rain_day)

    data object StreetCulture : FontMappingType(
        "Street Culture", R.font.street_culture, STREET_CULTURE, R.string.font_street_culture
    )

    data object NightPumpKind : FontMappingType(
        "Night Pump Kind", R.font.night_pump_kind, NIGHT_PUMP_KIND, R.string.font_night_pump_kind
    )

    data object LikeStyle :
        FontMappingType("Like Style", R.font.like_style, LIKE_STYLE, R.string.font_like_style)

    data object DearyBook :
        FontMappingType("Deary Book", R.font.deary_book, DEARY_BOOK, R.string.font_deary_book)

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