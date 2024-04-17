package com.apollo.timeflow.module.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.apollo.timeflow.R
import com.apollo.timeflow.component.DefaultText
import com.apollo.timeflow.module.moduleNavHost.NavHostRouteConfig
import com.apollo.timeflow.module.settings.utils.ENGLISH
import com.apollo.timeflow.module.settings.utils.SIMPLIFY_CHINESE
import com.apollo.timeflow.module.settings.utils.TRADITIONAL_CHINESE_FOR_HK
import com.apollo.timeflow.module.settings.utils.TRADITIONAL_CHINESE_FOR_TAIWAN
import com.apollo.timeflow.module.settings.utils.languageMapping
import com.apollo.timeflow.utils.getFontSizeInSetting
import com.apollo.timeflow.viewmodel.TimeViewModel

@Composable
fun ListDialog(
    navController: NavController,
    viewModelStoreOwner: ViewModelStoreOwner,
) {
    val timeViewModel = hiltViewModel<TimeViewModel>(viewModelStoreOwner)
    AlertDialog(
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
            val list = listOf(
                ENGLISH,
                SIMPLIFY_CHINESE,
                TRADITIONAL_CHINESE_FOR_HK,
                TRADITIONAL_CHINESE_FOR_TAIWAN,
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                list.forEach {
                    Text(
                        text = stringResource(id = it.languageMapping()),
                        fontSize = getFontSizeInSetting(timeViewModel.deviceUIState.value),
                        modifier = Modifier
                            .clickable {
                                navController.navigate("${NavHostRouteConfig.LANGUAGE_CONFIGURATION_CONFIRM_DIALOG_ROUTE}/$it")
                            }
                            .padding(12.dp),
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
        confirmButton = {},
        dismissButton = {},
    )
}