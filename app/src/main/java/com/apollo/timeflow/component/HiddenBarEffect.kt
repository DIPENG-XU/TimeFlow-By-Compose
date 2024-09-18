package com.apollo.timeflow.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * A Composable Component
 */
@Composable
fun HiddenBarEffect(systemUIController: SystemUiController = rememberSystemUiController()) {
    LaunchedEffect(key1 = Lifecycle.State.INITIALIZED) {
        systemUIController.isStatusBarVisible = false
        systemUIController.isNavigationBarVisible = false
    }
}