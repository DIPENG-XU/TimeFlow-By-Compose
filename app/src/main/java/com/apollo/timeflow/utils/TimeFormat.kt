package com.apollo.timeflow.utils

sealed class TimeFormat {
    data object Base12 : TimeFormat()
    data object Base24 : TimeFormat()
}