package com.apollo.timeflow.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TimeFlowBroadcastReceiver(
    private val _event: (() -> Unit),
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        _event.invoke()
    }
}