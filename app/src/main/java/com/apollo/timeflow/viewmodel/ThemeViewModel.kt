package com.apollo.timeflow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.apollo.timeflow.module.homefeed.service.feature.IThemeService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val iThemeService: IThemeService,
    application: Application,
) : AndroidViewModel(application) {

    val currentThemeFlow: Flow<Int> = iThemeService.themeFlow

    fun updateTheme() = viewModelScope.launch {
        val last = currentThemeFlow.stateIn(this).value
        val next = when (last) {
            in IThemeService.LIGHT_ALL_SET -> IThemeService.DARK
            in IThemeService.DARK_ALL_SET -> IThemeService.LIGHT
            else -> throw Exception("Unknown Type")
        }

        iThemeService.updateThemeRecord(next)
    }

    /**
     * ## Version 2.2.6 Update
     * - To Fixed the possible risk of burning the screen, every three minutes, the color will change slightly.
     * - For the time being, only changes to color in Light mode and Dark mode are added. If more theme changes are added later, these changes need to be considered.
     */
    fun autoUpdateThemeCauseProtected() = viewModelScope.launch {
        val next = when (val last = currentThemeFlow.stateIn(this).value) {
            in IThemeService.LIGHT_ALL_SET -> ((last - 1) % IThemeService.LIGHT_ALL_SET.size)
            in IThemeService.DARK_ALL_SET -> last % IThemeService.DARK_ALL_SET.size + 1
            else -> throw Exception("Unknown Type")
        }
        iThemeService.updateThemeRecord(next)
    }
}