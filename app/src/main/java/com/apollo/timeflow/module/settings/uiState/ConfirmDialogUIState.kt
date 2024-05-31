package com.apollo.timeflow.module.settings.uiState

import androidx.annotation.StringRes

data class ConfirmDialogUIState(
    @StringRes val pageName: Int,

    @StringRes val current: Int? = null,
    val currentString: String? = null,
    @StringRes val next: Int? = null,
    val nextString: String? = null,

    val onClickEvent: () -> Unit,
)