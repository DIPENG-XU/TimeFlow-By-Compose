package com.apollo.timeflow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.apollo.timeflow.module.homefeed.service.ThemeService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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
}