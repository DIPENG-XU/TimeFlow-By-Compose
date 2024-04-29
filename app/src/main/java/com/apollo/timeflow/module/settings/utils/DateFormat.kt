package com.apollo.timeflow.module.settings.utils

sealed class DateFormat(
    val dateFormat: String
) {
    data object YyyyMMdd : DateFormat("yyyy.MM.dd")
    data object MMddyyyy : DateFormat("MM.dd.yyyy")
    data object DdMMyyyy : DateFormat("dd.MM.YYYY")
}

val DATE_FORMAT_LIST = listOf(
    DateFormat.YyyyMMdd,
    DateFormat.MMddyyyy,
    DateFormat.DdMMyyyy
)