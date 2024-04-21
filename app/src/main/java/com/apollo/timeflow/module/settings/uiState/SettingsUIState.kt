package com.apollo.timeflow.module.settings.uiState

import androidx.annotation.StringRes


sealed class SettingsUIState {

    data object DivideUIState : SettingsUIState()

    class SettingsElementUIState(
        @StringRes val nameRes: Int,
    ) : SettingsUIState()
}