package com.apollo.timeflow.module.launch.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apollo.timeflow.R
import com.apollo.timeflow.component.HiddenBarEffect
import com.apollo.timeflow.module.launch.viewmodel.SplashViewModel
import com.apollo.timeflow.utils.DeviceUIState
import com.apollo.timeflow.utils.getFontSizeInHomeFeed
import com.apollo.timeflow.utils.getFontSizeInPowerBy
import com.apollo.timeflow.viewmodel.TimeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private val poppinsFontFamily = FontFamily(Font(R.font.poppins_bold, FontWeight.Light))

@Composable
fun LaunchPage(
    launchEvent: (() -> Unit) = { }
) {
    val timeViewModel = hiltViewModel<TimeViewModel>()
    val deviceType = remember(timeViewModel.deviceUIState.value) {
        timeViewModel.deviceUIState.value
    }

    Box {
        SloganText(deviceType)
        PowerText(deviceType)
    }

    LaunchInitUI(launchEvent)
    }

    HiddenBarEffect()
}

@Composable
private fun BoxScope.SloganText(
    deviceType: DeviceUIState,
) {
    val sloganText = hiltViewModel<SplashViewModel>().slogan.value
    Text(
        text = sloganText,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary,
        lineHeight = 48.sp,
        fontFamily = poppinsFontFamily,
        fontSize = getFontSizeInHomeFeed(deviceType),
        modifier = Modifier
            .align(Alignment.Center)
            .padding(12.dp),
    )

    }
}

@Composable
private fun BoxScope.PowerText(
    deviceType: DeviceUIState,
) {
    val powerByText = hiltViewModel<SplashViewModel>().powerBy.value
    Text(
        text = powerByText,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary,
        lineHeight = 48.sp,
        fontFamily = poppinsFontFamily,
        fontSize = getFontSizeInPowerBy(deviceType),
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(20.dp),
    )
}

@Composable
private fun LaunchInitUI(
    launchEvent: (() -> Unit)
) {
    val launchViewModel = hiltViewModel<SplashViewModel>()

    LaunchedEffect(key1 = Unit) {
        suspend fun fetchAndResideWelcomeSlogan() = withContext(Dispatchers.IO) {
            launchViewModel.fetchWelcomeSlogan()
            launchViewModel.fetchPowerBy()
            delay(5000L)
        }
        fetchAndResideWelcomeSlogan()
        launchEvent()
    }
}