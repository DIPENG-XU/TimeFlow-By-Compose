package com.apollo.timeflow

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Window
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.apollo.timeflow.basesupport.BaseActivity
import com.apollo.timeflow.broadcast.TimeFlowBroadcastReceiver
import com.apollo.timeflow.viewmodel.ThemeViewModel
import com.apollo.timeflow.viewmodel.TimeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimeActivity : BaseActivity("TimeActivity") {
    private val timeViewModel: TimeViewModel by viewModels<TimeViewModel>()
    private val themeViewModel: ThemeViewModel by viewModels<ThemeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            this.window.requestFeature(Window.FEATURE_NO_TITLE)
            this.addBroadcast()
        }

        setContent {
            TimeHostComponent(
                viewModelStoreOwner = this,
            )
        }
    }

    override fun onResume() {
        super.onResume()
        this.timeViewModel.updateDate()
    }

    private fun addBroadcast() {
        val intentFilterTimeChange = IntentFilter().apply {
            this.addAction(Intent.ACTION_TIME_TICK)
            this.addAction(Intent.ACTION_TIME_CHANGED)

            this.addAction(Intent.ACTION_DATE_CHANGED)

            this.addAction(Intent.ACTION_TIMEZONE_CHANGED)
            this.addAction(Intent.ACTION_LOCALE_CHANGED)
        }

        val timeChangeReceiver = TimeFlowBroadcastReceiver(
            updateTime = {
                this.timeViewModel.updateTime()
                this.themeViewModel.autoUpdateThemeCauseProtected()
            },
            updateDate = {
                this.timeViewModel.updateDate()
            }
        )

        ContextCompat.registerReceiver(
            applicationContext,
            timeChangeReceiver,
            intentFilterTimeChange,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }
}