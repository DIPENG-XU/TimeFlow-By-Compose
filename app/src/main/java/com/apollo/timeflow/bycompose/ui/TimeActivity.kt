package com.apollo.timeflow.bycompose.ui

import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.apollo.timeflow.bycompose.R
import com.apollo.timeflow.bycompose.broadcast.DateBroadcast
import com.apollo.timeflow.bycompose.broadcast.TimeBroadcast
import com.apollo.timeflow.bycompose.ui.screenAdaptation.CardHomeFeed
import com.apollo.timeflow.bycompose.ui.theme.TimeFlowTheme
import com.apollo.timeflow.bycompose.viewmodel.ThemeViewModel
import com.apollo.timeflow.bycompose.viewmodel.TimeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimeActivity : ComponentActivity() {
    private val mainViewModel: TimeViewModel by viewModels<TimeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.window.requestFeature(Window.FEATURE_NO_TITLE)
        this.parseColorToStatusBarAndNavigation()
        this.addBroadcast()

        this.mainViewModel.updateDate()

        setContent {
            TimeFlowTheme {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                ) {
                    CardHomeFeed()
                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideStatusAndNavigationBar()
    }

    private fun parseColorToStatusBarAndNavigation() {
        val color = ContextCompat.getColor(this, R.color.black)
        window.statusBarColor = color
        window.navigationBarColor = color
        hideStatusAndNavigationBar()
    }

    private fun hideStatusAndNavigationBar() {
        WindowCompat.getInsetsController(window, window.decorView).also {
            it.hide(WindowInsetsCompat.Type.statusBars())
            it.hide(WindowInsetsCompat.Type.navigationBars())
        }
    }

    private fun addBroadcast() {
        val intentFilterTimeChange = IntentFilter()
        intentFilterTimeChange.addAction(Intent.ACTION_TIME_TICK)
        intentFilterTimeChange.addAction(Intent.ACTION_TIME_CHANGED)
        intentFilterTimeChange.addAction(Intent.ACTION_TIMEZONE_CHANGED)
        intentFilterTimeChange.addAction(Intent.ACTION_LOCALE_CHANGED)

        val timeChangeReceiver = TimeBroadcast {
            mainViewModel.updateTime()
        }

        ContextCompat.registerReceiver(
            applicationContext,
            timeChangeReceiver,
            intentFilterTimeChange,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        val intentFilterDateChange = IntentFilter()
        intentFilterDateChange.addAction(Intent.ACTION_DATE_CHANGED)
        intentFilterDateChange.addAction(Intent.ACTION_TIMEZONE_CHANGED)
        intentFilterDateChange.addAction(Intent.ACTION_LOCALE_CHANGED)

        val dateChangeReceiver = DateBroadcast {
            mainViewModel.updateDate()
        }

        ContextCompat.registerReceiver(
            applicationContext,
            dateChangeReceiver,
            intentFilterDateChange,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }
}