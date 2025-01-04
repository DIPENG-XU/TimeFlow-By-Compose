package com.apollo.timeflow.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.apollo.timeflow.module.homefeed.service.feature.ITimeDataService
import com.apollo.timeflow.module.homefeed.service.feature.ITimeFormatRecordDataStoreService
import com.apollo.timeflow.module.homefeed.uistate.DateUIState
import com.apollo.timeflow.module.homefeed.uistate.TimeUIState
import com.apollo.timeflow.module.launch.service.feature.ISplashService
import com.apollo.timeflow.module.settings.service.feature.IDateFormatService
import com.apollo.timeflow.utils.BASE12
import com.apollo.timeflow.utils.BASE24
import com.apollo.timeflow.utils.DeviceUIState
import com.apollo.timeflow.utils.getDeviceType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimeViewModel @Inject constructor(
    private val iTimeFormatRecordService: ITimeFormatRecordDataStoreService,
    private val iTimeDataService: ITimeDataService,
    private val iDateFormatService: IDateFormatService,
    private val iLaunchService: ISplashService,
    private val _coroutineScope: CoroutineScope,
    application: Application,
) : AndroidViewModel(application) {

    private val _deviceUIState: MutableState<DeviceUIState> =
        mutableStateOf(getDeviceType(application))
    val deviceUIState: State<DeviceUIState> = _deviceUIState

    private var _timeFormat = MutableStateFlow(BASE12)
    private fun editTimeFormat(it: Boolean) = _coroutineScope.launch {
        _timeFormat.value = (if (it) BASE12 else BASE24)
        this@TimeViewModel.updateTime()
    }

    private val _timeUIState = mutableStateOf<TimeUIState?>(null)
    val timeUIState: State<TimeUIState?> = _timeUIState

    fun updateTime() = _coroutineScope.launch {
        val timeFormat = _timeFormat.value
        _timeUIState.value = iTimeDataService.getCurrentTime(timeFormat)
    }

    private val _currentDateFlow: MutableStateFlow<String> = MutableStateFlow("")
    fun updateDate() = _coroutineScope.launch {
        val dateFormatPattern = dateFormatUIState.stateIn(this).value
        _currentDateFlow.emit(iTimeDataService.getCurrentDate(dateFormatPattern))
    }

    private fun updateDate(dateFormatPattern: String) = _coroutineScope.launch {
        _currentDateFlow.emit(iTimeDataService.getCurrentDate(dateFormatPattern))
    }

    val dateUIStateFlow: Flow<DateUIState> =
        _currentDateFlow.combine(iTimeFormatRecordService.dateFlow) { currentDate, showOrHide ->
            DateUIState(
                showOrHide = showOrHide,
                currentDate = currentDate,
            )
        }

    fun updateDateDisplayOrNot() = _coroutineScope.launch {
        val isDateDisplay = dateUIStateFlow.stateIn(this).value
        iTimeFormatRecordService.updateDateRecord(isDateDisplay.showOrHide xor true)
    }


    val timeFormatRecordDataStoreFlow = iTimeFormatRecordService.timeFormatFlow.map {
        this.editTimeFormat(it)
        it
    }

    fun updateTimeFormat() = _coroutineScope.launch {
        val timeFormat = timeFormatRecordDataStoreFlow.stateIn(this).value
        iTimeFormatRecordService.updateTimeFormat(timeFormat xor true)
    }


    val dateFormatUIState: Flow<String> = iDateFormatService.dateFormatFlow.map {
        this.updateDate(it)
        it
    }

    fun updateDateFormat(dateFormat: String) = _coroutineScope.launch {
        iDateFormatService.updateThemeRecord(dateFormat)
    }

    val powerByShowOrHideStoreFlow: Flow<Boolean> = iLaunchService.powerByShowOrHideStateFlow

    fun updatePowerByShowOrHide() = _coroutineScope.launch {
        iLaunchService.updatePowerByShowOrHide(!powerByShowOrHideStoreFlow.stateIn(_coroutineScope).value)
    }

    override fun onCleared() {
        super.onCleared()
        _coroutineScope.cancel()
    }
}