package com.apollo.timeflow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.apollo.timeflow.module.homefeed.service.feature.IThemeService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val iThemeService: IThemeService,
    private val coroutine: CoroutineContext,
    application: Application,
) : AndroidViewModel(application) {

    val currentThemeFlow: Flow<Int> = iThemeService.themeFlow

    fun updateTheme() = viewModelScope.launch(coroutine) {
        val last = currentThemeFlow.stateIn(this).value
        iThemeService.updateThemeRecord(last xor 1)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}