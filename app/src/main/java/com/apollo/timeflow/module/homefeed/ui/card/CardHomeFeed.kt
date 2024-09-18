package com.apollo.timeflow.module.homefeed.ui.card

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.ViewModelStoreOwner
import com.apollo.timeflow.component.HiddenBarEffect

@Composable
fun CardHomeFeed(
    viewModelStoreOwner: ViewModelStoreOwner,
    navigateClickable: (() -> Unit)? = null,
) {
    val isPortrait = (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT)
    if (isPortrait) {
        CardPortrait(viewModelStoreOwner, navigateClickable)
    } else {
        CardLandScape(viewModelStoreOwner, navigateClickable)
    }

    HiddenBarEffect()
}