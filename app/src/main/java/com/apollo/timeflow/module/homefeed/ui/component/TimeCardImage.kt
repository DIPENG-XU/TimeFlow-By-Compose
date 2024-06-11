package com.apollo.timeflow.module.homefeed.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.apollo.timeflow.R
import com.apollo.timeflow.utils.imageHash

@Composable
fun RowScope.TimeCardImage(
    number: Int = 0,
) {
    Image(
        colorFilter = ColorFilter.tint(
            color = MaterialTheme.colorScheme.primary,
        ),
        painter = painterResource(
            id = imageHash[number] ?: R.drawable.ic_number0,
        ),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .weight(1f)
            .background(MaterialTheme.colorScheme.background)
    )
}