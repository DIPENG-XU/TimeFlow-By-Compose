package com.apollo.timeflow

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Window
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.apollo.timeflow.basesupport.BaseActivity
import com.apollo.timeflow.broadcast.TimeFlowBroadcastReceiver
import com.apollo.timeflow.module.settings.utils.FontMappingType
import com.apollo.timeflow.viewmodel.ThemeViewModel
import com.apollo.timeflow.viewmodel.TimeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimeActivity : BaseActivity("TimeActivity"), TimeFlowBroadcastReceiver.ITimeCallback {
    private val timeViewModel: TimeViewModel by viewModels<TimeViewModel>()
    private val themeViewModel: ThemeViewModel by viewModels<ThemeViewModel>()

    private lateinit var timeChangeReceiver: TimeFlowBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            this.window.requestFeature(Window.FEATURE_NO_TITLE)
        }

        this.addBroadcast()

        setContent {
            val fontName = themeViewModel.fontFlow.collectAsStateWithLifecycle(
                initialValue = FontMappingType.PoppinsBold.name
            ).value

            val navController = rememberNavController()

            CompositionLocalProvider(
                RootConfig.LocalFontNameConfig provides fontName,
                RootConfig.LocalActivityViewModelStoreOwner provides this@TimeActivity
            ) {
                TimeHostComponent(navController)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        this.onUpdateTime()
        this.onUpdateDate()
    }

    private fun addBroadcast() {
        val intentFilterTimeChange = IntentFilter().apply {
            this.addAction(Intent.ACTION_TIME_TICK)
            this.addAction(Intent.ACTION_TIME_CHANGED)
            this.addAction(Intent.ACTION_DATE_CHANGED)
            this.addAction(Intent.ACTION_TIMEZONE_CHANGED)
            this.addAction(Intent.ACTION_LOCALE_CHANGED)
        }

        timeChangeReceiver = TimeFlowBroadcastReceiver()
        timeChangeReceiver.addCallback(this)
        ContextCompat.registerReceiver(
            applicationContext,
            timeChangeReceiver,
            intentFilterTimeChange,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::timeChangeReceiver.isInitialized) {
            try {
                timeChangeReceiver.removeCallback(this)
                unregisterReceiver(timeChangeReceiver)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }

    override fun onUpdateTime() {
        if (this.isDestroyed) return

        this.timeViewModel.updateTime()
        this.themeViewModel.autoUpdateThemeCauseProtected()
    }

    override fun onUpdateDate() {
        if (this.isDestroyed) return

        this.timeViewModel.updateDate()
    }
}