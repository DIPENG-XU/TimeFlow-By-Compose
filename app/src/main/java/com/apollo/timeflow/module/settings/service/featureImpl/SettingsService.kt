package com.apollo.timeflow.module.settings.service.featureImpl

import com.apollo.timeflow.R
import com.apollo.timeflow.module.settings.service.feature.ISettingsService
import com.apollo.timeflow.module.settings.uiState.SettingsUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsService @Inject constructor(
    private val coroutineScope: CoroutineScope,
): ISettingsService {
    override suspend fun fetchSettingsUIState(): List<SettingsUIState> =
        withContext(coroutineScope.coroutineContext) {
            val list = mutableListOf<SettingsUIState>()

            val originResList = listOf(
                R.string.theme_mode,
                R.string.time_format,
                R.string.update_the_date_display,
                R.string.update_date_format,
                R.string.update_language,
            )

            originResList.forEach {
                list.add(SettingsUIState.SettingsElementUIState(it))
                list.add(SettingsUIState.DivideUIState)
            }

            list
        }
}