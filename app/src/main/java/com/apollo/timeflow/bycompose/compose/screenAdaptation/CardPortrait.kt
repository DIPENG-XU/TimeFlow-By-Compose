package com.apollo.timeflow.bycompose.compose.screenAdaptation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.apollo.timeflow.bycompose.getFontSize
import com.apollo.timeflow.bycompose.viewmodel.TimeViewModel

@Preview(
    widthDp = 411,
    heightDp = 872,
)
@Composable
fun CardPortrait() {
    val viewModel: TimeViewModel = hiltViewModel()
    val timeFormat = viewModel.timeFormatRecordDataStoreFlow.collectAsState(initial = false)
    val dateFormat = viewModel.isDateShowDataStoreFlow.collectAsState(initial = false)
    val timeUIState = viewModel.timeUIState.value ?: return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center),
        ) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                TimeCard(
                    deviceTypes = viewModel.deviceType.value,
                    clickable = {
                        viewModel.timeFormatRecordUpdate(!timeFormat.value)
                    },
                    isTimeFormat = timeFormat.value,
                    amOrPm = stringResource(id = timeUIState.amOrPM),
                    leftNumber = timeUIState.hoursLeft,
                    rightNumber = timeUIState.hoursRight,
                )
            }

            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                TimeCard(
                    deviceTypes = viewModel.deviceType.value,
                    clickable = {
                        viewModel.isDateShow(!dateFormat.value)
                    },
                    isTimeFormat = false,
                    amOrPm = null,
                    leftNumber = timeUIState.minutesLeft,
                    rightNumber = timeUIState.minutesRight,
                )
            }
        }

        if (dateFormat.value) {
            Text(
                viewModel.currentDate.value,
                color = Color.White,
                fontSize = getFontSize(viewModel.deviceType.value),
                fontFamily = defaultFontFamily,
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .align(Alignment.BottomCenter),
            )
        }
    }
}