package com.apollo.timeflow.module.settings.utils

import androidx.annotation.StringRes
import com.apollo.timeflow.R

const val ENGLISH = "en"
const val SIMPLIFY_CHINESE = "zh-CN"
const val TRADITIONAL_CHINESE_FOR_HK = "zh-HK"
const val TRADITIONAL_CHINESE_FOR_TAIWAN = "zh-TW"

@StringRes
fun String.languageMapping(): Int {
    return when (this) {
        ENGLISH -> R.string.english
        SIMPLIFY_CHINESE -> R.string.chinese_zh_cn
        TRADITIONAL_CHINESE_FOR_HK -> R.string.chinese_zh_hk
        TRADITIONAL_CHINESE_FOR_TAIWAN -> R.string.chinese_zh_tw
        else -> throw Exception("Unknown Type")
    }
}