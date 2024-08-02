package com.apollo.timeflow.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.pow
import kotlin.math.sqrt


@Suppress("DEPRECATION")
internal fun getDeviceType(context: Context): DeviceUIState {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val dm = DisplayMetrics()
    display.getMetrics(dm)
    val x = ((dm.widthPixels / dm.xdpi)).toDouble().pow(2.0)
    val y = ((dm.heightPixels / dm.ydpi)).toDouble().pow(2.0)
    val screenInches = sqrt(x + y)
    return when {
        (screenInches < 7.0f) -> DeviceUIState.Phone()
        (screenInches >= 7.0f && screenInches < 15.0f) -> DeviceUIState.Tablet()
        else -> DeviceUIState.TV()
    }
}

internal fun getFontSizeInPowerBy(deviceType: DeviceUIState): TextUnit = when (deviceType) {
    is DeviceUIState.Phone -> 18.sp
    is DeviceUIState.Tablet -> 24.sp
    is DeviceUIState.TV -> 24.sp
}

internal fun getFontSizeInHomeFeed(deviceType: DeviceUIState): TextUnit = when (deviceType) {
    is DeviceUIState.Phone -> 24.sp
    is DeviceUIState.Tablet -> 36.sp
    is DeviceUIState.TV -> 36.sp
}

internal fun getFontSizeInSetting(deviceType: DeviceUIState): TextUnit = when (deviceType) {
    is DeviceUIState.Phone -> 18.sp
    is DeviceUIState.Tablet -> 24.sp
    is DeviceUIState.TV -> 24.sp
}

sealed class DeviceUIState {
    abstract val timeCardWidth: Dp
    abstract val timeCardHeight: Dp

    class Phone(
        override val timeCardWidth: Dp = 120.dp,
        override val timeCardHeight: Dp = 300.dp,
    ) : DeviceUIState()

    class Tablet(
        override val timeCardWidth: Dp = 160.dp,
        override val timeCardHeight: Dp = 400.dp,
    ) : DeviceUIState()

    class TV(
        override val timeCardWidth: Dp = 200.dp,
        override val timeCardHeight: Dp = 500.dp,
    ) : DeviceUIState()
}
