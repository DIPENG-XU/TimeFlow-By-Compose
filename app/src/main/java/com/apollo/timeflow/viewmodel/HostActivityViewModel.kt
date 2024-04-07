package com.apollo.timeflow.viewmodel

import android.app.Application
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HostActivityViewModel(
    application: Application,
) : AndroidViewModel(
    application = application
) {
    private val _snackbarHostState = SnackbarHostState()
    val snackbarHostState = _snackbarHostState

    fun showSnackbar(snackTips: String) {
        viewModelScope.launch {
            _snackbarHostState.showSnackbar(snackTips)
        }
    }
}