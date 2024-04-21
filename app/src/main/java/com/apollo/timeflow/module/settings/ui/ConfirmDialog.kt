package com.apollo.timeflow.module.settings.ui

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.apollo.timeflow.R
import com.apollo.timeflow.component.DefaultText
import com.apollo.timeflow.module.homefeed.uistate.DateUIState
import com.apollo.timeflow.module.moduleNavHost.NavHostLanguageConfigurationConfirmDialogArgument
import com.apollo.timeflow.module.moduleNavHost.NavHostRouteConfig
import com.apollo.timeflow.module.settings.utils.mappedToAStringResByName
import com.apollo.timeflow.utils.getFontSizeInSetting
import com.apollo.timeflow.viewmodel.HostActivityViewModel
import com.apollo.timeflow.viewmodel.ThemeViewModel
import com.apollo.timeflow.viewmodel.TimeViewModel

@Composable
fun ConfirmDialog(
    navController: NavController,
    route: String,
    viewModelStoreOwner: ViewModelStoreOwner,
    bundle: Bundle = Bundle(),
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
                NavHostLanguageConfigurationConfirmDialogArgument.SELECTED_AREA, "zh-CN"
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

        else -> throw Exception("Unknown Route, Please check it again!")
    }

    val fontSize = getFontSizeInSetting(timeViewModel.deviceUIState.value)

    val hostActivityViewModel = hiltViewModel<HostActivityViewModel>(viewModelStoreOwner)
    AlertDialog(
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
                            stringResource(id = confirmDialogUIState.current),
                        ),
                        fontSize = fontSize,
                    )
                }
                item {
                    DefaultText(
                        text = String.format(
                            stringResource(id = R.string.new_state_confirm),
                            stringResource(id = confirmDialogUIState.next),
                        ),
                        fontSize = fontSize,
                    )
                }
            }
        },

        onDismissRequest = {
            navController.popBackStack(
                NavHostRouteConfig.NAV_HOST_ROUTE_FOR_SETTINGS,
                inclusive = false,
            )
        },

        confirmButton = {
            val successTips = String.format(
                stringResource(id = R.string.success_to_update),
                stringResource(id = confirmDialogUIState.pageName),
                stringResource(id = confirmDialogUIState.next),
            )
            TextButton(onClick = {
                confirmDialogUIState.onClickEvent.invoke()
                navController.popBackStack(
                    NavHostRouteConfig.NAV_HOST_ROUTE_FOR_SETTINGS,
                    inclusive = true,
                )
                hostActivityViewModel.showSnackbar(successTips)
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
                navController.popBackStack(
                    NavHostRouteConfig.NAV_HOST_ROUTE_FOR_SETTINGS,
                    inclusive = false,
                )
                hostActivityViewModel.showSnackbar(dismissTips)
            }) {
                DefaultText(
                    text = stringResource(id = R.string.dismiss),
                    fontSize = fontSize,
                )
            }
        },
    )
}


data class ConfirmDialogUIState(
    @StringRes val pageName: Int,
    @StringRes val current: Int,
    @StringRes val next: Int,
    val onClickEvent: () -> Unit,
)