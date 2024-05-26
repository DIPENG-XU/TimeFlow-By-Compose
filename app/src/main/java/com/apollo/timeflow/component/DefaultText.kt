package com.apollo.timeflow.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import com.apollo.timeflow.utils.defaultFontFamily

@Composable
fun DefaultText(
    text: String,
    fontSize: TextUnit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontFamily = defaultFontFamily,
        color = Color.Black,
        modifier = modifier
    )
}