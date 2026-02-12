package com.apollo.timeflow.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TimeFlowBroadcastReceiver : BroadcastReceiver() {
    private val callbacks = mutableListOf<java.lang.ref.WeakReference<ITimeCallback>>()

    fun addCallback(callback: ITimeCallback) {
        cleanInvalidCallbacks()
        callbacks.add(java.lang.ref.WeakReference(callback))
    }

    fun removeCallback(callback: ITimeCallback) {
        callbacks.removeAll { it.get() == callback || it.get() == null }
    }

    private fun cleanInvalidCallbacks() {
        callbacks.removeAll { it.get() == null }
    }

    override fun onReceive(context: Context?, intent: Intent) {
        cleanInvalidCallbacks()

        when (intent.action) {
            Intent.ACTION_TIME_TICK,
            Intent.ACTION_TIME_CHANGED -> {
                callbacks.forEach { ref ->
                    ref.get()?.onUpdateTime()
                }
            }

            Intent.ACTION_DATE_CHANGED -> {
                callbacks.forEach { ref ->
                    ref.get()?.onUpdateDate()
                }
            }

            Intent.ACTION_TIMEZONE_CHANGED,
            Intent.ACTION_LOCALE_CHANGED -> {
                callbacks.forEach { ref ->
                    ref.get()?.onUpdateTime()
                    ref.get()?.onUpdateDate()
                }
            }
        }
    }

    interface ITimeCallback {
        fun onUpdateTime()
        fun onUpdateDate()
    }
}