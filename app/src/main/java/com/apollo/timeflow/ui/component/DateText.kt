package com.apollo.timeflow.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apollo.timeflow.utils.defaultFontFamily
import com.apollo.timeflow.utils.DeviceUIState
import com.apollo.timeflow.utils.getFontSize

@Composable
fun BoxScope.DateText(
    currentDate: String,
    deviceUIState: DeviceUIState,
    onClick: () -> Unit,
) {
    Text(
        text = currentDate,
        color = MaterialTheme.colorScheme.primary,
        fontSize = getFontSize(deviceUIState),
        fontFamily = defaultFontFamily,
        modifier = Modifier
            .padding(bottom = 10.dp)
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null,
                onClick = onClick
            )
            .align(Alignment.BottomCenter),
    )
}