package com.apollo.timeflow.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.apollo.timeflow.viewmodel.ThemeViewModel

private val LightColors = lightColorScheme(
    primary = m3_grey_900,
    background = m3_grey50,
)

private val DarkColors = darkColorScheme(
    primary = m3_white,
    background = m3_black,
)

@Composable
fun TimeFlowTheme(
    content: @Composable () -> Unit
) {
    val viewModel = hiltViewModel<ThemeViewModel>()
    val currentTheme = viewModel.currentThemeFlow.collectAsState(initial = 0).value
    val colors = when (currentTheme) {
        0 -> LightColors
        else -> DarkColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}