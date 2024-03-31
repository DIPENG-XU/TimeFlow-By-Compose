package com.apollo.timeflow.bycompose.compose.screenAdaptation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apollo.timeflow.bycompose.compose.component.TimeCard
import com.apollo.timeflow.bycompose.defaultFontFamily
import com.apollo.timeflow.bycompose.uistate.DateUIState
import com.apollo.timeflow.bycompose.utils.getFontSize
import com.apollo.timeflow.bycompose.viewmodel.TimeViewModel

@Preview(widthDp = 411, heightDp = 872)
@Composable
fun CardLandSpace() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
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
            Text(
                dateUIState.currentDate,
                color = Color.White,
                fontSize = getFontSize(deviceUIState),
                fontFamily = defaultFontFamily,
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .align(Alignment.BottomCenter),
            )
        }
    }
}