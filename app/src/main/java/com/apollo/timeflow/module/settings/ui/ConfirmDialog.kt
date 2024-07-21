package com.apollo.timeflow.module.settings.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.apollo.timeflow.R
import com.apollo.timeflow.component.DefaultText
import com.apollo.timeflow.module.homefeed.uistate.DateUIState
import com.apollo.timeflow.module.moduleNavHost.NavHostDateFormatSelectorConfigurationConfirmDialogArgument
import com.apollo.timeflow.module.moduleNavHost.NavHostLanguageConfigurationConfirmDialogArgument
import com.apollo.timeflow.module.moduleNavHost.NavHostRouteConfig
import com.apollo.timeflow.module.settings.uiState.ConfirmDialogUIState
import com.apollo.timeflow.module.settings.utils.LanguageType
import com.apollo.timeflow.module.settings.utils.mappedToAStringResByName
import com.apollo.timeflow.utils.getFontSizeInSetting
import com.apollo.timeflow.viewmodel.HostActivityViewModel
import com.apollo.timeflow.viewmodel.ThemeViewModel
import com.apollo.timeflow.viewmodel.TimeViewModel

@Composable
fun ConfirmDialog(
    route: String,
    viewModelStoreOwner: ViewModelStoreOwner,
    bundle: Bundle = Bundle(),
    navigatePopBack: ((String, Boolean) -> Unit) = { _, _ -> }
) {
    val timeViewModel = hiltViewModel<TimeViewModel>(viewModelStoreOwner)
    val confirmDialogUIState = when (route) {
        NavHostRouteConfig.THEME_FORMAT_DIALOG_ROUTE -> {
            val themeViewModel = hiltViewModel<ThemeViewModel>(viewModelStoreOwner)
            val (current, next) = if (themeViewModel.currentThemeFlow.collectAsState(initial = 0).value == 0) {
                R.string.light_mode to R.string.dark_mode
            } else {
                R.string.dark_mode to R.string.light_mode
            }

            ConfirmDialogUIState(pageName = R.string.theme_mode,
                current = current,
                next = next,
                onClickEvent = {
                    themeViewModel.updateTheme()
                })
        }

        NavHostRouteConfig.TIME_FORMAT_DIALOG_ROUTE -> {
            val (current, next) = if (timeViewModel.timeFormatRecordDataStoreFlow.collectAsState(
                    initial = false
                ).value
            ) {
                R.string.base12 to R.string.base24
            } else {
                R.string.base24 to R.string.base12
            }
            ConfirmDialogUIState(pageName = R.string.time_format,
                current = current,
                next = next,
                onClickEvent = {
                    timeViewModel.updateTimeFormat()
                })
        }

        NavHostRouteConfig.DATE_FORMAT_DIALOG_ROUTE -> {
            val (current, next) = if (timeViewModel.dateUIStateFlow.collectAsState(initial = DateUIState()).value.showOrHide) {
                R.string.open to R.string.close
            } else {
                R.string.close to R.string.open
            }
            ConfirmDialogUIState(pageName = R.string.update_the_date_display,
                current = current,
                next = next,
                onClickEvent = {
                    timeViewModel.updateDateDisplayOrNot()
                })
        }

        NavHostRouteConfig.LANGUAGE_CONFIGURATION_CONFIRM_DIALOG_ROUTE -> {
            val currentLanguage = AppCompatDelegate.getApplicationLocales().toLanguageTags()
            val nextLanguage = bundle.getString(
                NavHostLanguageConfigurationConfirmDialogArgument.SELECTED_AREA,
                LanguageType.English.name,
            )
            val (current, next) = currentLanguage.mappedToAStringResByName() to nextLanguage.mappedToAStringResByName()

            ConfirmDialogUIState(pageName = R.string.update_language_confirm_title,
                current = current,
                next = next,
                onClickEvent = {
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(nextLanguage)
                    )
                })
        }

        NavHostRouteConfig.DATE_FORMAT_SELECTOR_CONFIRM_DIALOG_ROUTE -> {
            val current = timeViewModel.dateFormatUIState.collectAsState(initial = "")
            val next = bundle.getString(
                NavHostDateFormatSelectorConfigurationConfirmDialogArgument.SELECTED_DATE_FORMAT
            )

            ConfirmDialogUIState(
                pageName = R.string.update_date_format,
                currentString = current.value,
                nextString = next,
                onClickEvent = {
                    next?.let { timeViewModel.updateDateFormat(it) }
                }
            )
        }

        NavHostRouteConfig.POWER_BY_DIALOG_ROUTE -> {
            val (current, next) = if (timeViewModel.powerByShowOrHideStoreFlow.collectAsState(
                    initial = true
                ).value
            ) {
                R.string.open to R.string.close
            } else {
                R.string.close to R.string.open
            }
            ConfirmDialogUIState(
                pageName = R.string.config_power_by,
                current = current,
                next = next,
                onClickEvent = {
                    timeViewModel.updatePowerByShowOrHide()
                }
            )
        }


        else -> throw Exception("Unknown Route, Please check it again!")
    }

    val fontSize = getFontSizeInSetting(timeViewModel.deviceUIState.value)

    val hostActivityViewModel = hiltViewModel<HostActivityViewModel>(viewModelStoreOwner)
    AlertDialog(
        containerColor = Color.White,
        shape = RoundedCornerShape(size = 8.dp),
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 0.dp,
        title = {
            DefaultText(
                text = String.format(
                    stringResource(id = R.string.title_confirm),
                    stringResource(id = confirmDialogUIState.pageName)
                ),
                fontSize = fontSize,
            )
        },

        text = {
            LazyColumn {
                item {
                    DefaultText(
                        text = String.format(
                            stringResource(id = R.string.current_state_confirm),
                            confirmDialogUIState.current?.let { stringResource(id = it) }
                                ?: confirmDialogUIState.currentString,
                        ),
                        fontSize = fontSize,
                    )
                }
                item {
                    DefaultText(
                        text = String.format(
                            stringResource(id = R.string.new_state_confirm),
                            confirmDialogUIState.next?.let { stringResource(id = it) }
                                ?: confirmDialogUIState.nextString,
                        ),
                        fontSize = fontSize,
                    )
                }
            }
        },

        onDismissRequest = {
            navigatePopBack.invoke(
                NavHostRouteConfig.NAV_HOST_ROUTE_FOR_SETTINGS,
                false,
            )
        },

        confirmButton = {
            val successTips = String.format(
                stringResource(id = R.string.success_to_update),
                stringResource(id = confirmDialogUIState.pageName),
                confirmDialogUIState.next?.let { stringResource(id = confirmDialogUIState.next) }
                    ?: confirmDialogUIState.nextString,
            )
            TextButton(onClick = {
                confirmDialogUIState.onClickEvent.invoke()
                navigatePopBack.invoke(
                    NavHostRouteConfig.NAV_HOST_ROUTE_FOR_SETTINGS,
                     true,
                )
                hostActivityViewModel.showSnackBar(successTips)
            }) {
                DefaultText(
                    text = stringResource(id = R.string.confirm),
                    fontSize = fontSize,
                )
            }
        },

        dismissButton = {
            val dismissTips = String.format(
                stringResource(id = R.string.dismiss_to_update),
            )
            TextButton(onClick = {
                navigatePopBack.invoke(
                    NavHostRouteConfig.NAV_HOST_ROUTE_FOR_SETTINGS,
                    false,
                )
                hostActivityViewModel.showSnackBar(dismissTips)
            }) {
                DefaultText(
                    text = stringResource(id = R.string.dismiss),
                    fontSize = fontSize,
                )
            }
        },
    )
}


