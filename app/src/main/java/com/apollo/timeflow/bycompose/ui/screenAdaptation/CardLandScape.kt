package com.apollo.timeflow.bycompose.ui.screenAdaptation

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.apollo.timeflow.bycompose.ui.component.DateText
import com.apollo.timeflow.bycompose.ui.component.TimeCard
import com.apollo.timeflow.bycompose.uistate.DateUIState
import com.apollo.timeflow.bycompose.viewmodel.ThemeViewModel
import com.apollo.timeflow.bycompose.viewmodel.TimeViewModel

@Preview(widthDp = 411, heightDp = 872)
@Composable
fun CardLandScape() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        val viewModel: TimeViewModel = hiltViewModel()
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
                    clickable = {
                        viewModel.updateTimeFormat(!timeFormat.value)
                    },
                    isTimeFormat = timeFormat.value,
                    amOrPm = stringResource(id = timeUIState.amOrPM),
                    leftNumber = timeUIState.hoursLeft,
                    rightNumber = timeUIState.hoursRight,
                )
            }
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                TimeCard(
                    deviceUIState = deviceUIState,
                    clickable = {
                        viewModel.updateDateRecord(!dateUIState.showOrHide)
                    },
                    isTimeFormat = false,
                    amOrPm = null,
                    leftNumber = timeUIState.minutesLeft,
                    rightNumber = timeUIState.minutesRight,
                )
            }
        }

        if (dateUIState.showOrHide) {
            val themeViewModel = hiltViewModel<ThemeViewModel>()
            DateText(
                currentDate = dateUIState.currentDate,
                deviceUIState = deviceUIState,
                onClick = {
                    themeViewModel.updateTheme()
                }
            )
        }
    }
}