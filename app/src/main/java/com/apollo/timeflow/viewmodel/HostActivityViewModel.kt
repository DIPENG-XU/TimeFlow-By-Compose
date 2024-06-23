package com.apollo.timeflow.viewmodel

import android.app.Application
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class HostActivityViewModel @Inject constructor(
    private val coroutine: CoroutineContext,
    application: Application,
) : AndroidViewModel(
    application = application
) {
    private val _snackBarHostState = SnackbarHostState()
    val snackBarHostState = _snackBarHostState

    fun showSnackBar(snackTips: String) = viewModelScope.launch(coroutine) {
        _snackBarHostState.currentSnackbarData?.dismiss()
        _snackBarHostState.showSnackbar(
            snackTips,
            duration = SnackbarDuration.Short,
            withDismissAction = true
        )
    }
}