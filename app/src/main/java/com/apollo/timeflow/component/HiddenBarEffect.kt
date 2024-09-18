package com.apollo.timeflow.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import com.apollo.timeflow.module.homefeed.ui.card.CardHomeFeed
import com.apollo.timeflow.module.launch.ui.LaunchPage
import com.apollo.timeflow.module.settings.ui.TimeFlowSettings
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

 /**
 * ## A Composable Component
 * - Use in FullScreen Composable Jetpack Compose Component.
 * - Now it is used in [CardHomeFeed], [LaunchPage] and [TimeFlowSettings]
 */
@Composable
fun HiddenBarEffect(systemUIController: SystemUiController = rememberSystemUiController()) {
    LaunchedEffect(key1 = Lifecycle.State.INITIALIZED) {
        systemUIController.isStatusBarVisible = false
        systemUIController.isNavigationBarVisible = false
    }
}