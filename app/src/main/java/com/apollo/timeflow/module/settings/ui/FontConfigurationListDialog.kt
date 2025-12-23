package com.apollo.timeflow.module.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.apollo.timeflow.R
import com.apollo.timeflow.component.DefaultText
import com.apollo.timeflow.module.moduleNavHost.NavHostRouteConfig
import com.apollo.timeflow.module.settings.component_ui.SettingDialogListUIComponent
import com.apollo.timeflow.module.settings.utils.FONT_LIST
import com.apollo.timeflow.module.settings.utils.FontMappingType
import com.apollo.timeflow.utils.getFontSizeInSetting
import com.apollo.timeflow.viewmodel.ThemeViewModel
import com.apollo.timeflow.viewmodel.TimeViewModel

@Composable
fun FontConfigurationListDialog(
    viewModelStoreOwner: ViewModelStoreOwner,
    navigateClickable: ((String) -> Unit) = { },
    navigatePopBackStack: (() -> Unit) = { },
) {

    val deviceUIState = hiltViewModel<TimeViewModel>(viewModelStoreOwner)
        .deviceUIState
        .value

    val currentFontName = hiltViewModel<ThemeViewModel>(viewModelStoreOwner)
        .fontFlow
        .collectAsState(FontMappingType.PoppinsBold.name)
        .value

    SettingDialogListUIComponent(
        deviceUIState = deviceUIState,
        navigatePopBackStack = navigatePopBackStack,
        items = {
            val operateFont = FONT_LIST.filter { font -> font.name != currentFontName }
            items(operateFont.size) { index ->
                val font = operateFont.getOrNull(index) ?: return@items
                val fontName = font.name

                DefaultText(
                    text = fontName,
                    fontSize = getFontSizeInSetting(deviceUIState),
                    modifier = Modifier
                        .clickable {
                            navigateClickable.invoke("${NavHostRouteConfig.Dialog.FontConfig.CONFIRM}/${font.name}")
                        }
                        .padding(12.dp),
                )
            }
        },

        title = {
            DefaultText(
                text = stringResource(id = R.string.update_font_confirm_title),
                fontSize = getFontSizeInSetting(deviceUIState),
            )
        }
    )
}