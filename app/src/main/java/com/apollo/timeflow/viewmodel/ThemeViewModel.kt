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
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val _iThemeService: IThemeService,
    private val _coroutine: CoroutineContext,
    application: Application,
) : AndroidViewModel(application) {

    val currentThemeFlow: Flow<Int> = _iThemeService.themeFlow

    fun updateTheme() = viewModelScope.launch(_coroutine) {
        val last = currentThemeFlow.stateIn(this).value
        _iThemeService.updateThemeRecord(last xor 1)
    }
}