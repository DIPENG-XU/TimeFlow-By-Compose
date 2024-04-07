package com.apollo.timeflow.module.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.apollo.timeflow.R
import com.apollo.timeflow.component.DefaultText
import com.apollo.timeflow.module.settings.uiState.SettingsUIState
import com.apollo.timeflow.module.settings.viewmodel.SettingsViewModel
import com.apollo.timeflow.module.moduleNavHost.NavHostRouteConfig
import com.apollo.timeflow.utils.defaultFontFamily
import com.apollo.timeflow.utils.getFontSizeInSetting
import com.apollo.timeflow.viewmodel.TimeViewModel

@Composable
fun TimeFlowSettings(
    navHostController: NavHostController,
    viewModelStoreOwner: ViewModelStoreOwner,
) {
    val timeViewModel: TimeViewModel = hiltViewModel(viewModelStoreOwner)
    val viewModel: SettingsViewModel = hiltViewModel(viewModelStoreOwner)

    viewModel.fetchSettings()
    viewModel.fetchVersion()

    val uiState = viewModel.settingsUIState.value
    val versionCode = viewModel.packageVersionName.value

    val fontSize = getFontSizeInSetting(timeViewModel.deviceUIState.value)

    Box {
        LazyColumn(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .wrapContentHeight()
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
                        navHostController = navHostController,
                        fontSize = fontSize,
                    )
                }
            }
        }

        DefaultText(
            text = String.format(stringResource(id = R.string.current_version, versionCode)),
            fontSize = fontSize,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp)
        )
    }
}

@Composable
private fun SettingsElementItem(
    settingsElementUIState: SettingsUIState.SettingsElementUIState,
    navHostController: NavHostController,
    fontSize: TextUnit,
) {
    Box(
        modifier = Modifier
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                }, indication = null, onClick = {
                    val navigateRoute = when (settingsElementUIState.nameRes) {
                        R.string.theme_mode -> NavHostRouteConfig.THEME_FORMAT_DIALOG_ROUTE
                        R.string.update_the_date_display -> NavHostRouteConfig.DATE_FORMAT_DIALOG_ROUTE
                        R.string.time_format -> NavHostRouteConfig.TIME_FORMAT_DIALOG_ROUTE
                        else -> NavHostRouteConfig.THEME_FORMAT_DIALOG_ROUTE
                    }
                    navHostController.navigate(navigateRoute)
                }
            )
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

@Preview
@Composable
private fun SettingsElementItemPreview() {
    SettingsElementItem(
        settingsElementUIState = SettingsUIState.SettingsElementUIState(
            R.string.theme_mode,
        ),
        navHostController = rememberNavController(),
        fontSize = 16.sp,
    )
}