package com.apollo.timeflow.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        maxLines = 1,
        fontSize = fontSize,
        fontFamily = defaultFontFamily,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    )
}