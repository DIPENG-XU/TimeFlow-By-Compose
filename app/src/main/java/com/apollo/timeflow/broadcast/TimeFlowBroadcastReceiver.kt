package com.apollo.timeflow.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TimeFlowBroadcastReceiver(
    private val updateTime: () -> Unit,
    private val updateDate: () -> Unit,
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_TIME_TICK,
            Intent.ACTION_TIME_CHANGED, -> {
                updateTime.invoke()
            }

            Intent.ACTION_DATE_CHANGED -> {
                updateDate.invoke()
            }

            Intent.ACTION_TIMEZONE_CHANGED,
            Intent.ACTION_LOCALE_CHANGED, -> {
                updateTime.invoke()
                updateDate.invoke()
            }
        }
    }
}