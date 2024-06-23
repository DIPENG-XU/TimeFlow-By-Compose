package com.apollo.timeflow

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Window
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import com.apollo.timeflow.basesupport.BaseActivity
import com.apollo.timeflow.broadcast.TimeFlowBroadcastReceiver
import com.apollo.timeflow.viewmodel.TimeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimeActivity : BaseActivity("TimeActivity") {
    private val timeViewModel: TimeViewModel by viewModels<TimeViewModel>()

    private val onDestinationChangedListener =
        NavController.OnDestinationChangedListener { _, _, _ ->
            this@TimeActivity.hideStatusAndNavigationBar()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            this.window.requestFeature(Window.FEATURE_NO_TITLE)
            this.addBroadcast()
        }

        setContent {
            TimeHostComponent(
                onDestinationChangedListener = onDestinationChangedListener,
                viewModelStoreOwner = this,
            )
        }
    }

    override fun onResume() {
        super.onResume()
        this.timeViewModel.updateDate()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
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

        val timeChangeReceiver = TimeFlowBroadcastReceiver {
            timeViewModel.updateTime()
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

        val dateChangeReceiver = TimeFlowBroadcastReceiver {
            timeViewModel.updateDate()
        }

        ContextCompat.registerReceiver(
            applicationContext,
            dateChangeReceiver,
            intentFilterDateChange,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }
}