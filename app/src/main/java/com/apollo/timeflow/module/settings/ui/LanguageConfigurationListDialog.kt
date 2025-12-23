package com.apollo.timeflow.module.settings.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.apollo.timeflow.R
import com.apollo.timeflow.component.DefaultText
import com.apollo.timeflow.module.moduleNavHost.NavHostRouteConfig
import com.apollo.timeflow.module.settings.component_ui.SettingDialogListUIComponent
import com.apollo.timeflow.module.settings.utils.LANGUAGE_LIST
import com.apollo.timeflow.utils.getFontSizeInSetting
import com.apollo.timeflow.viewmodel.TimeViewModel

@Composable
fun LanguageConfigurationListDialog(
    viewModelStoreOwner: ViewModelStoreOwner,
    navigateClickable: ((String) -> Unit) = { },
    navigatePopBackStack: (() -> Unit) = { },
) {
    val timeViewModel = hiltViewModel<TimeViewModel>(viewModelStoreOwner)
    SettingDialogListUIComponent(
        deviceUIState = timeViewModel.deviceUIState.value,
        navigatePopBackStack = navigatePopBackStack,
        items = {
            val currentLanguage = AppCompatDelegate.getApplicationLocales().toLanguageTags()

            val operateLanguage = LANGUAGE_LIST.filter { language -> language.name != currentLanguage }
            items(operateLanguage.size) { index ->
                val language = operateLanguage.getOrNull(index) ?: return@items

                DefaultText(
                    text = stringResource(id = language.stringRes),
                    fontSize = getFontSizeInSetting(timeViewModel.deviceUIState.value),
                    modifier = Modifier
                        .clickable {
                            navigateClickable.invoke("${NavHostRouteConfig.Dialog.LanguageConfig.CONFIRM}/${language.name}")
                        }
                        .padding(12.dp),
                )
            }
        },

        title = {
            DefaultText(
                text = stringResource(id = R.string.update_language_confirm_title),
                fontSize = getFontSizeInSetting(timeViewModel.deviceUIState.value),
            )
        }
    )
}