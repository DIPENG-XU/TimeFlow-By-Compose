package com.apollo.timeflow.module.settings.utils

import androidx.annotation.StringRes
import com.apollo.timeflow.R

sealed class LanguageType(
    val name: String,
    @StringRes val stringRes: Int,
) {
    data object English : LanguageType("en", R.string.english)

    data object SimplifyChinese : LanguageType("zh-CN", R.string.chinese_zh_cn)

    data object TraditionalChineseForHK : LanguageType("zh-HK", R.string.chinese_zh_hk)

    data object TraditionalChineseForTW : LanguageType("zh-TW", R.string.chinese_zh_tw)
}

val LANGUAGE_LIST = listOf(
    LanguageType.English,
    LanguageType.SimplifyChinese,
    LanguageType.TraditionalChineseForHK,
    LanguageType.TraditionalChineseForTW,
)

@StringRes
fun String.mappedToAStringResByName(): Int {
    if (this.isEmpty()) return R.string.system_language

    LANGUAGE_LIST.forEach {
        if (this == it.name) return it.stringRes
    }
    return R.string.unknown_language
}