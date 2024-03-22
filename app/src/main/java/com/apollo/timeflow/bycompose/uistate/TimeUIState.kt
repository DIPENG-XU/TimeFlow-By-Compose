package com.apollo.timeflow.bycompose.uistate

import androidx.annotation.StringRes

data class TimeUIState(
    val hoursLeft: Int,
    val hoursRight: Int,
    val minutesLeft: Int,
    val minutesRight: Int,
    @StringRes val amOrPM: Int,
)