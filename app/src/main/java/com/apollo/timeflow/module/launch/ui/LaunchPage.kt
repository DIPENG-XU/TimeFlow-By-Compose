package com.apollo.timeflow.module.launch.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import com.apollo.timeflow.R
import com.apollo.timeflow.module.launch.viewmodel.LaunchViewModel
import com.apollo.timeflow.module.moduleNavHost.NavHostRouteConfig
import com.apollo.timeflow.utils.getFontSizeInHomeFeed
import com.apollo.timeflow.viewmodel.TimeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun LaunchPage(
    viewModelStoreOwner: ViewModelStoreOwner,
    navController: NavHostController,
) {
    val timeViewModel = hiltViewModel<TimeViewModel>(viewModelStoreOwner)
    val deviceType = timeViewModel.deviceUIState.value

    val launchViewModel = hiltViewModel<LaunchViewModel>(viewModelStoreOwner)

    Box {
        Text(
            launchViewModel.slogan.value,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            lineHeight = 48.sp,
            fontFamily = FontFamily(
                fonts = listOf(
                    Font(R.font.poppins_bold, FontWeight.Light),
                )
            ),
            fontSize = getFontSizeInHomeFeed(deviceType),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(12.dp),
        )
    }

    LaunchedEffect(key1 = Lifecycle.State.CREATED) {
        suspend fun fetchAndResideWelcomeSlogan() = withContext(Dispatchers.IO){
            launchViewModel.fetchWelcomeSlogan()
            delay(5000L)
        }
        fetchAndResideWelcomeSlogan()
        navController.popBackStack(NavHostRouteConfig.NAV_HOST_LAUNCH_PAGE, true)
        navController.navigate(NavHostRouteConfig.NAV_HOST_ROUTE_FOR_HOMEFEED)
    }
}

