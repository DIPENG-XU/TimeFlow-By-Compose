package com.apollo.timeflow.bycompose.compose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apollo.timeflow.bycompose.Device
import com.apollo.timeflow.bycompose.R
import com.apollo.timeflow.bycompose.getFontSize
import com.apollo.timeflow.bycompose.imageHash


@Preview(showBackground = true)
@Composable
fun TimeCard(
    deviceTypes: Device = Device.Phone(),
    clickable: () -> Unit = {},
    isTimeFormat: Boolean = true,
    amOrPm: String? = "AM",
    leftNumber: Int = 0,
    rightNumber: Int = 1,
) {
    val width = deviceTypes.width
    val height = deviceTypes.height
    Scaffold(
        modifier = Modifier
            .clickable(onClick = clickable)
            .width(width * 2)
            .height(height)
            .background(Color.Black),
    ) {
        it.calculateTopPadding()
        it.calculateBottomPadding()
        Box(
            modifier = Modifier
                .width(width * 2)
                .height(height)
                .background(Color.Black)
                .padding(20.dp),
        ) {
            Row(
                modifier = Modifier.align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Center,
            ) {
                Image(
                    painter = painterResource(
                        id = imageHash[leftNumber] ?: R.drawable.ic_number0
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Black),
                )
                Image(
                    painter = painterResource(
                        id = imageHash[rightNumber] ?: R.drawable.ic_number0
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Black)
                )
            }
            if (isTimeFormat) {
                Text(
                    amOrPm ?: "AM",
                    modifier = Modifier.align(Alignment.BottomEnd),
                    color = Color.White,
                    fontFamily = FontFamily(
                        fonts = listOf(Font(R.font.poppins_bold, FontWeight.Light))
                    ),
                    fontSize = getFontSize(deviceTypes),
                )
            }
        }
    }
}