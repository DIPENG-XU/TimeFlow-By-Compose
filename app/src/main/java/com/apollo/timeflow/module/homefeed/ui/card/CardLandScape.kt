package com.apollo.timeflow.module.homefeed.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.apollo.timeflow.module.homefeed.ui.component.DateText
import com.apollo.timeflow.module.homefeed.ui.component.TimeCard
import com.apollo.timeflow.module.homefeed.uistate.DateUIState
import com.apollo.timeflow.module.moduleNavHost.NavHostRouteConfig
import com.apollo.timeflow.utils.monitorRecombination
import com.apollo.timeflow.viewmodel.TimeViewModel

@Composable
fun CardLandScape(
    viewModelStoreOwner: ViewModelStoreOwner,
    navController: NavController,
) {
    "CardLandScape recombination".monitorRecombination()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        val viewModel: TimeViewModel = hiltViewModel(viewModelStoreOwner)
        val timeFormat = viewModel.timeFormatRecordDataStoreFlow.collectAsState(initial = false)
        val timeUIState = viewModel.timeUIState.value ?: return
        val dateUIState = viewModel.dateUIStateFlow.collectAsState(initial = DateUIState()).value
        val deviceUIState = viewModel.deviceUIState.value
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Center),
        ) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                TimeCard(
                    deviceUIState = deviceUIState,
                    isTimeFormat = timeFormat.value,
                    clickable = {
                        navController.navigate(NavHostRouteConfig.NAV_HOST_ROUTE_FOR_SETTINGS)
                    },
                    amOrPm = stringResource(id = timeUIState.amOrPM),
                    leftNumber = timeUIState.hoursLeft,
                    rightNumber = timeUIState.hoursRight,
                )
            }
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                TimeCard(
                    deviceUIState = deviceUIState,
                    isTimeFormat = false,
                    clickable = null,
                    amOrPm = null,
                    leftNumber = timeUIState.minutesLeft,
                    rightNumber = timeUIState.minutesRight,
                )
            }
        }

        if (dateUIState.showOrHide) {
            DateText(
                currentDate = dateUIState.currentDate,
                deviceUIState = deviceUIState,
            )
        }
    }
}