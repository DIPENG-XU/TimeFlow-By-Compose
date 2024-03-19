package com.apollo.timeflow.bycompose.compose.screenAdaptation

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import com.apollo.timeflow.bycompose.viewmodel.MainViewModel


@Preview(
    showBackground = true,
    widthDp = 872,
    heightDp = 411,
)
@Composable
fun Card(
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    val isPortrait = (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT)
    if (isPortrait) {
        CardPortrait(viewModel)
    } else {
        CardLandSpace(viewModel)
    }
}