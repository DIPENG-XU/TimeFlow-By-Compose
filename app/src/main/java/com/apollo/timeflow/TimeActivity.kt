package com.apollo.timeflow

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Window
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.apollo.timeflow.broadcast.DateBroadcast
import com.apollo.timeflow.broadcast.TimeBroadcast
import com.apollo.timeflow.module.homefeed.ui.theme.TimeFlowTheme
import com.apollo.timeflow.module.moduleNavHost.TimeFlowNavHost
import com.apollo.timeflow.viewmodel.HostActivityViewModel
import com.apollo.timeflow.viewmodel.TimeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TimeActivity : AppCompatActivity() {
    private val mainViewModel: TimeViewModel by viewModels<TimeViewModel>()

    private val hostViewModel: HostActivityViewModel by viewModels<HostActivityViewModel>()

    private val onDestinationChangedListener =
        NavController.OnDestinationChangedListener { _, _, _ ->
            this@TimeActivity.hideStatusAndNavigationBar()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.window.requestFeature(Window.FEATURE_NO_TITLE)
        this.parseColorToStatusBarAndNavigation()
        this.addBroadcast()

        this.mainViewModel.updateDate()

        setContent {
            TimeFlowTheme {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = hostViewModel.snackbarHostState)
                    }
                ) { paddingValues ->
                    paddingValues.calculateTopPadding()
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                    ) {
                        val navController = rememberNavController().also {
                            it.removeOnDestinationChangedListener(onDestinationChangedListener)
                            it.addOnDestinationChangedListener(onDestinationChangedListener)
                        }

                        TimeFlowNavHost(
                            viewModelStoreOwner = this@TimeActivity,
                            navController = navController,
                        )
                    }
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