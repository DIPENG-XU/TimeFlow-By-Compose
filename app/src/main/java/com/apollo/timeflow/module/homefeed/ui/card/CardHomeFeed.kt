package com.apollo.timeflow.module.homefeed.ui.card

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.apollo.timeflow.component.HiddenBarEffect

@Composable
fun CardHomeFeed(
    navigateClickable: (() -> Unit)? = null,
) {
    val isPortrait = (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT)
    if (isPortrait) {
        CardPortrait(navigateClickable)
    } else {
        CardLandScape(navigateClickable)
    }

    HiddenBarEffect()
}