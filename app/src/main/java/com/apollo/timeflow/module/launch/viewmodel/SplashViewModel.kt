package com.apollo.timeflow.module.launch.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.apollo.timeflow.R
import com.apollo.timeflow.module.launch.service.feature.ISplashService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val _coroutineScope: CoroutineScope,
    private val _iLaunchService: ISplashService,
    private val _application: Application,
): AndroidViewModel(_application) {
    private val _slogan: MutableState<String> = mutableStateOf("")
    val slogan: State<String> = _slogan

    fun fetchWelcomeSlogan() = _coroutineScope.launch {
        val timeStage = async {
            _iLaunchService.fetchTimeStage()
        }
        val dayOfWeek = async {
            _iLaunchService.fetchDayOfWeek()
        }
        withContext(Dispatchers.Main) {
            _slogan.value = String.format(
                _application.getString(
                    R.string.welcome_in_launch_page
                ),
                _application.getString(
                    dayOfWeek.await().stringResource,
                ),
                _application.getString(
                    timeStage.await().stringResource,
                )
            )
        }
    }

    private val _powerBy: MutableState<String> = mutableStateOf("")
    val powerBy: State<String> = _powerBy

    fun fetchPowerBy() = _coroutineScope.launch {
        _powerBy.value = if (_iLaunchService.powerByShowOrHideStateFlow.stateIn(_coroutineScope).value) {
            _application.getString(_iLaunchService.fetchPowerByStringResource())
        } else ""
    }

    override fun onCleared() {
        super.onCleared()
        _coroutineScope.cancel()
    }
}