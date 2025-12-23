package com.apollo.timeflow.module.settings.component_ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apollo.timeflow.R
import com.apollo.timeflow.component.DefaultText
import com.apollo.timeflow.utils.DeviceUIState
import com.apollo.timeflow.utils.getFontSizeInSetting
import com.apollo.timeflow.viewmodel.TimeViewModel

/**
 * **The setting Dialog List UI Component By Jetpack Compose**
 * That is to use to build a List Struct UI Structuration
 *
 * @param deviceUIState: Current Device UIState, almost use the [TimeViewModel.deviceUIState]
 */
@Composable
fun SettingDialogListUIComponent(
    deviceUIState: DeviceUIState,
    navigatePopBackStack: () -> Unit,
    items: LazyListScope.() -> Unit,
    title: @Composable () -> Unit,
) {
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
                title()
            }
        },
        text = {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                items()
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

@Preview
@Composable
fun SettingDialogListUIComponentPreview() {
    val mockkDeviceUIState = DeviceUIState.Phone
    val mockkItemResource = listOf(
        R.string.english,
        R.string.chinese_zh_cn,
        R.string.chinese_zh_hk,
        R.string.chinese_zh_jp,
        R.string.chinese_zh_ko,
    )

    SettingDialogListUIComponent(
        deviceUIState = mockkDeviceUIState,
        navigatePopBackStack = {

        },
        items = {
            items(mockkItemResource.size) { index ->
                val stringResId = mockkItemResource.getOrNull(index) ?: return@items

                DefaultText(
                    text = stringResource(id = stringResId),
                    fontSize = getFontSizeInSetting(mockkDeviceUIState),
                    modifier = Modifier
                        .clickable {}
                        .padding(12.dp),
                )
            }
        },

        title = {
            DefaultText(
                text = stringResource(id = R.string.update_language_confirm_title),
                fontSize = getFontSizeInSetting(mockkDeviceUIState),
            )
        }
    )
}