package com.apollo.timeflow.viewmodel

import android.app.Application
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HostActivityViewModel @Inject constructor(
    private val _coroutineScope: CoroutineScope,
    application: Application,
) : AndroidViewModel(
    application = application
) {
    private val _snackBarHostState = SnackbarHostState()
    val snackBarHostState = _snackBarHostState

    fun showSnackBar(snackTips: String) = _coroutineScope.launch {
        _snackBarHostState.currentSnackbarData?.dismiss()
        _snackBarHostState.showSnackbar(
            snackTips,
            duration = SnackbarDuration.Short,
            withDismissAction = true
        )
    }

    override fun onCleared() {
        super.onCleared()
        _coroutineScope.cancel()
    }
}