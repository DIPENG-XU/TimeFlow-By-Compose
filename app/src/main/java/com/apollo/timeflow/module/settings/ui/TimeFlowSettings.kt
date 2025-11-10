package com.apollo.timeflow.module.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStoreOwner
import com.apollo.timeflow.R
import com.apollo.timeflow.component.DefaultText
import com.apollo.timeflow.component.HiddenBarEffect
import com.apollo.timeflow.module.moduleNavHost.NavHostRouteConfig
import com.apollo.timeflow.module.settings.uiState.SettingsUIState
import com.apollo.timeflow.module.settings.viewmodel.SettingsViewModel
import com.apollo.timeflow.utils.defaultFontFamily
import com.apollo.timeflow.utils.getFontSizeInSetting
import com.apollo.timeflow.viewmodel.TimeViewModel

@Composable
fun TimeFlowSettings(
    viewModelStoreOwner: ViewModelStoreOwner,
    navigateEvent: ((String) -> Unit)? = null,
) {
    val timeViewModel: TimeViewModel = hiltViewModel(viewModelStoreOwner)
    val viewModel: SettingsViewModel = hiltViewModel(viewModelStoreOwner)

    val uiState = viewModel.settingsUIState.value
    val versionCode = viewModel.packageVersionName.value

    val fontSize = getFontSizeInSetting(timeViewModel.deviceUIState.value)

    val listState = rememberLazyListState()

    val isListScrollable = remember {
        derivedStateOf {
            listState.canScrollForward || listState.canScrollBackward
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState
        ) {
            items(uiState.size) {
                val item = uiState.getOrNull(it) ?: return@items

                when (item) {
                    SettingsUIState.DivideUIState -> HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        color = MaterialTheme.colorScheme.primary,
                    )

                    is SettingsUIState.SettingsElementUIState -> SettingsElementItem(
                        settingsElementUIState = item,
                        fontSize = fontSize,
                        navigateEvent = navigateEvent,
                    )
                }
            }

            if (isListScrollable.value) {
                item {
                    VersionFooter(
                        versionCode = versionCode,
                        fontSize = fontSize
                    )
                }
            }
        }

        if (!isListScrollable.value) {
            VersionFooter(
                versionCode = versionCode,
                fontSize = fontSize,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }

    LaunchedEffect(key1 = Lifecycle.State.CREATED) {
        viewModel.fetchSettings()
        viewModel.fetchVersion()
    }

    HiddenBarEffect()
}

@Composable
fun VersionFooter(
    versionCode: String,
    fontSize: TextUnit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        DefaultText(
            text = stringResource(
                id = R.string.current_version,
                versionCode
            ),
            fontSize = fontSize,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(12.dp)
        )
    }
}


@Composable
private fun SettingsElementItem(
    settingsElementUIState: SettingsUIState.SettingsElementUIState,
    fontSize: TextUnit,
    navigateEvent: ((String) -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .clickable(interactionSource = remember {
                MutableInteractionSource()
            }, indication = null, onClick = {
                val navigateRoute = when (settingsElementUIState.nameRes) {
                    R.string.theme_mode -> NavHostRouteConfig.Dialog.THEME_FORMAT
                    R.string.update_the_date_display -> NavHostRouteConfig.Dialog.DATE_FORMAT
                    R.string.update_date_format -> NavHostRouteConfig.Dialog.DateFormatSelector.ROUTE
                    R.string.time_format -> NavHostRouteConfig.Dialog.TIME_FORMAT
                    R.string.update_language -> NavHostRouteConfig.Dialog.LanguageConfig.ROUTE
                    R.string.config_power_by -> NavHostRouteConfig.Dialog.POWER_BY
                    else -> NavHostRouteConfig.Dialog.THEME_FORMAT
                }
                navigateEvent?.invoke(navigateRoute)
            })
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = settingsElementUIState.nameRes),
                color = MaterialTheme.colorScheme.primary,
                fontSize = fontSize,
                fontFamily = defaultFontFamily,
                maxLines = 1,
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterVertically)
            )

            Icon(
                Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .wrapContentSize(),
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}