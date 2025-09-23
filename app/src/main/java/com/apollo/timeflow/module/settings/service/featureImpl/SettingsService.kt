package com.apollo.timeflow.module.settings.service.featureImpl

import com.apollo.timeflow.R
import com.apollo.timeflow.module.settings.service.feature.ISettingsService
import com.apollo.timeflow.module.settings.uiState.SettingsUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsService @Inject constructor(): ISettingsService {
    override suspend fun fetchSettingsUIState(): List<SettingsUIState> = withContext(Dispatchers.IO) {
        val list = mutableListOf<SettingsUIState>()

        val originResList = listOf(
            R.string.theme_mode,
            R.string.time_format,
            R.string.update_the_date_display,
            R.string.update_date_format,
            R.string.config_power_by,
            R.string.update_language,
        )

        originResList.forEach {
            list.add(SettingsUIState.SettingsElementUIState(it))
            list.add(SettingsUIState.DivideUIState)
        }

        list
    }
}