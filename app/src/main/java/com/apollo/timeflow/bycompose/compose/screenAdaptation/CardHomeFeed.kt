package com.apollo.timeflow.bycompose.compose.screenAdaptation

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true, widthDp = 411, heightDp = 872)
@Composable
fun CardHomeFeed() {
    val isPortrait = (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT)
    if (isPortrait) {
        CardPortrait()
    } else {
        CardLandSpace()
    }
}