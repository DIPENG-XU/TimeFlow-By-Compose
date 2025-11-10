package com.apollo.timeflow.module.settings.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.apollo.timeflow.R
import com.apollo.timeflow.component.DefaultText
import com.apollo.timeflow.module.moduleNavHost.NavHostRouteConfig
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
    AlertDialog(
        containerColor = Color.White,
        shape = RoundedCornerShape(size = 8.dp),
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 0.dp,
        title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                DefaultText(
                    text = stringResource(id = R.string.update_language_confirm_title),
                    fontSize = getFontSizeInSetting(timeViewModel.deviceUIState.value),
                )
            }
        },
        text = {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                val currentLanguage = AppCompatDelegate.getApplicationLocales().toLanguageTags()

                val operateLanguage = LANGUAGE_LIST.filter { it.name != currentLanguage }
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

            }
        },
        onDismissRequest = navigatePopBackStack,
        confirmButton = {
            // ignored
        },
        dismissButton = {
            // ignored
        },
    )
}