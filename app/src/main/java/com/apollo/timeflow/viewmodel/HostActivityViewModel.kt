package com.apollo.timeflow.viewmodel

import android.app.Application
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HostActivityViewModel @Inject constructor(
    application: Application,
) : AndroidViewModel(
    application = application
) {
    private val _snackbarHostState = SnackbarHostState()
    val snackbarHostState = _snackbarHostState

    fun showSnackbar(snackTips: String) {
        viewModelScope.launch {
            _snackbarHostState.currentSnackbarData?.dismiss()
            _snackbarHostState.showSnackbar(
                snackTips,
                duration = SnackbarDuration.Short,
                withDismissAction = true
            )
        }
    }
}