package com.apollo.timeflow.module.settings.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.apollo.timeflow.module.settings.service.feature.ISettingsService
import com.apollo.timeflow.module.settings.uiState.SettingsUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val iSettingsService: ISettingsService,
    private val application: Application,
) : AndroidViewModel(application) {
    private val _settingsUIState = mutableStateOf<List<SettingsUIState>>(listOf())
    val settingsUIState: State<List<SettingsUIState>> = _settingsUIState

    fun fetchSettings() = viewModelScope.launch {
        _settingsUIState.value = withContext(Dispatchers.Main) {
            iSettingsService.fetchSettingsUIState()
        }
    }

    private val _packageVersionName = mutableStateOf("")
    val packageVersionName: State<String> = _packageVersionName

    fun fetchVersion() = viewModelScope.launch {
        _packageVersionName.value = withContext(Dispatchers.Main) {
            application
                .packageManager
                .getPackageInfo(application.packageName, 0)
                .versionName ?: ""
        }
    }
}