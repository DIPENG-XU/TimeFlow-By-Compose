package com.apollo.timeflow.module.homefeed.ui.component

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apollo.timeflow.utils.DeviceUIState
import com.apollo.timeflow.utils.defaultFontFamily
import com.apollo.timeflow.utils.getFontSizeInHomeFeed

@Composable
fun BoxScope.DateText(
    currentDate: String,
    deviceUIState: DeviceUIState,
) {
    Text(
        text = currentDate,
        color = MaterialTheme.colorScheme.primary,
        fontSize = getFontSizeInHomeFeed(deviceUIState),
        fontFamily = defaultFontFamily,
        modifier = Modifier
            .padding(bottom = 10.dp)
            .align(Alignment.BottomCenter),
    )
}