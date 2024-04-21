package com.apollo.timeflow.module.homefeed.ui.card

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController

@Composable
fun CardHomeFeed(
    viewModelStoreOwner: ViewModelStoreOwner,
    navController: NavController,
) {
    val isPortrait = (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT)
    if (isPortrait) {
        CardPortrait(viewModelStoreOwner, navController)
    } else {
        CardLandScape(viewModelStoreOwner, navController)
    }
}