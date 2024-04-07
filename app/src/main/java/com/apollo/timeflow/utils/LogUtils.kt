package com.apollo.timeflow.utils

import android.util.Log

fun String.logd(tag: String = "TimeFlowLog") {
    Log.d(tag, this)
}

fun String.monitorRecombination() {
    Log.d("MonitorRecombination", this)
}