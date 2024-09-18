package com.apollo.timeflow.utils

import android.util.Log

internal fun String.logD(tag: String = "TimeFlowLog") {
    Log.d(tag, this)
}

internal fun String.monitorRecombination() {
    Log.d("MonitorRecombination", this)
}