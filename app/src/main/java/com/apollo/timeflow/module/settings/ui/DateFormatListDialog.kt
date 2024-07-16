package com.apollo.timeflow.module.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.apollo.timeflow.R
import com.apollo.timeflow.component.DefaultText
import com.apollo.timeflow.module.moduleNavHost.NavHostRouteConfig
import com.apollo.timeflow.module.settings.utils.DATE_FORMAT_LIST
import com.apollo.timeflow.utils.getFontSizeInSetting
import com.apollo.timeflow.viewmodel.TimeViewModel

@Composable
fun DateFormatListDialog(
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
                    text = stringResource(id = R.string.update_date_format),
                    fontSize = getFontSizeInSetting(timeViewModel.deviceUIState.value),
                )
            }
        },

        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                val currentDateFormatPattern = timeViewModel.dateFormatUIState.collectAsState(
                    initial = ""
                ).value

                DATE_FORMAT_LIST
                    .filter { it.dateFormat != currentDateFormatPattern }
                    .forEach {
                        DefaultText(
                            text = it.dateFormat,
                            fontSize = getFontSizeInSetting(timeViewModel.deviceUIState.value),
                            modifier = Modifier
                                .clickable(
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    },
                                    indication = null,
                                ) {
                                    navigateClickable.invoke("${NavHostRouteConfig.DATE_FORMAT_SELECTOR_CONFIRM_DIALOG_ROUTE}/${it.dateFormat}")
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