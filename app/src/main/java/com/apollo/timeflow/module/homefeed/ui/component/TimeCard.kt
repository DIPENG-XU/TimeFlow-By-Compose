package com.apollo.timeflow.module.homefeed.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apollo.timeflow.utils.DeviceUIState
import com.apollo.timeflow.utils.defaultFontFamily
import com.apollo.timeflow.utils.getFontSizeInHomeFeed


@Preview(showBackground = true)
@Composable
fun TimeCard(
    deviceUIState: DeviceUIState = DeviceUIState.Phone(),
    clickable: (() -> Unit)? = null,
    isTimeFormat: Boolean = true,
    amOrPm: String? = "AM",
    leftNumber: Int = 0,
    rightNumber: Int = 1,
) {
    val width = deviceUIState.timeCardWidth
    val height = deviceUIState.timeCardHeight
    Box(
        modifier = Modifier
            .clickable(
                onClick = clickable ?: {},
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null,
            )
            .width(width * 2)
            .height(height)
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp),
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Center,
        ) {
            TimeCardImage(number = leftNumber)
            TimeCardImage(number = rightNumber)
        }
        if (isTimeFormat) {
            Text(
                amOrPm ?: "AM",
                modifier = Modifier.align(Alignment.BottomEnd),
                color = MaterialTheme.colorScheme.primary,
                fontFamily = defaultFontFamily,
                fontSize = getFontSizeInHomeFeed(deviceUIState),
            )
        }
    }
}
