package com.apollo.timeflow.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.apollo.timeflow.module.homefeed.service.feature.IThemeService
import com.apollo.timeflow.viewmodel.ThemeViewModel

private val LightColors = lightColorScheme(
    primary = m3_grey_900,
    background = m3_grey50,
)

private val LightColorsProtected = lightColorScheme(
    primary = m3_grey_900,
    background = m3_offWhite,
)

private val DarkColors = darkColorScheme(
    primary = m3_white,
    background = m3_black,
)

private val DarkColorsProtected = darkColorScheme(
    primary = m3_offWhite,
    background = m3_black,
)

@Composable
fun TimeFlowTheme(
    content: @Composable () -> Unit
) {
    val viewModel = hiltViewModel<ThemeViewModel>()
    val currentTheme = viewModel.currentThemeFlow.collectAsState(initial = 0).value
    val colors = when (currentTheme) {
        IThemeService.LIGHT -> LightColors
        IThemeService.LIGHT_PROTECTED -> LightColorsProtected
        IThemeService.DARK -> DarkColors
        IThemeService.DARK_PROTECTED -> DarkColorsProtected
        else -> throw Exception("Unknown Type")
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}