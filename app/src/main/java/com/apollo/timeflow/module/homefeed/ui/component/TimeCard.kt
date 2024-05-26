package com.apollo.timeflow.module.homefeed.ui.component

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apollo.timeflow.R
import com.apollo.timeflow.utils.imageHash
import com.apollo.timeflow.utils.DeviceUIState
import com.apollo.timeflow.utils.getFontSizeInHomeFeed


@Preview(showBackground = true)
@Composable
fun TimeCard(
    deviceUIState: DeviceUIState = DeviceUIState.Phone(),
    clickable: () -> Unit = {},
    isTimeFormat: Boolean = true,
    amOrPm: String? = "AM",
    leftNumber: Int = 0,
    rightNumber: Int = 1,
) {
    val width = deviceUIState.width
    val height = deviceUIState.height
    Box(
        modifier = Modifier
            .clickable(
                onClick = clickable,
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
            Image(
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.primary,
                ),
                painter = painterResource(
                    id = imageHash[leftNumber] ?: R.drawable.ic_number0,
                ),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.background),
            )
            Image(
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.primary,
                ),
                painter = painterResource(
                    id = imageHash[rightNumber] ?: R.drawable.ic_number0,
                ),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.background)
            )
        }
        if (isTimeFormat) {
            Text(
                amOrPm ?: "AM",
                modifier = Modifier.align(Alignment.BottomEnd),
                color = MaterialTheme.colorScheme.primary,
                fontFamily = FontFamily(
                    fonts = listOf(
                        Font(R.font.poppins_bold, FontWeight.Light),
                    )
                ),
                fontSize = getFontSizeInHomeFeed(deviceUIState),
            )
        }
    }
}
