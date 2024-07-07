package com.apollo.timeflow.module.launch.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.apollo.timeflow.R
import com.apollo.timeflow.module.launch.service.feature.ILaunchService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class LaunchViewModel @Inject constructor(
    private val _coroutine: CoroutineContext,
    private val _iLaunchService: ILaunchService,
    private val _application: Application,
): AndroidViewModel(_application) {
    private val _slogan: MutableState<String> = mutableStateOf("")
    val slogan: State<String> = _slogan

    fun fetchWelcomeSlogan() = viewModelScope.launch(_coroutine) {
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

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}