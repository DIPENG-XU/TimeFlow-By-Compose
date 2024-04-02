package com.apollo.timeflow.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.apollo.timeflow.service.ThemeService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeService: ThemeService,
    application: Application,
) : AndroidViewModel(application) {

    /**
     * 0 -> Light
     * 1 -> Dark
     */
    val currentThemeFlow: Flow<Int> = themeService.themeFlow

    fun updateTheme() {
        viewModelScope.launch {
            val last = currentThemeFlow.stateIn(this).value
            themeService.updateThemeRecord(last xor 1)
        }
    }

    /**
     * If support more theme later, use this and deprecate the [updateTheme]
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun updateTheme(value: Int) {
        viewModelScope.launch {
            themeService.updateThemeRecord(value)
        }
    }
}