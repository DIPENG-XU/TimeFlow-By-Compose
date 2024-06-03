package com.apollo.timeflow.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import com.apollo.timeflow.utils.defaultFontFamily

@Composable
fun DefaultText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit,
    color: Color = Color.Black,
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontFamily = defaultFontFamily,
        color = color,
        modifier = modifier
    )
}