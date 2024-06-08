package com.apollo.timeflow.module.settings.service.feature

import com.apollo.timeflow.module.settings.uiState.SettingsUIState

interface ISettingsService {
    suspend fun fetchSettingsUIState(): List<SettingsUIState>
}