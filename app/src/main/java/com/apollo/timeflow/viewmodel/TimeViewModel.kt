package com.apollo.timeflow.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.apollo.timeflow.module.homefeed.service.feature.ITimeDataService
import com.apollo.timeflow.module.homefeed.service.feature.ITimeFormatRecordDataStoreService
import com.apollo.timeflow.module.homefeed.uistate.DateUIState
import com.apollo.timeflow.module.homefeed.uistate.TimeUIState
import com.apollo.timeflow.module.launch.service.feature.ILaunchService
import com.apollo.timeflow.module.settings.service.feature.IDateFormatService
import com.apollo.timeflow.utils.DeviceUIState
import com.apollo.timeflow.utils.TimeFormat
import com.apollo.timeflow.utils.getDeviceType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class TimeViewModel @Inject constructor(
    private val iTimeFormatRecordService: ITimeFormatRecordDataStoreService,
    private val iTimeDataService: ITimeDataService,
    private val iDateFormatService: IDateFormatService,
    private val iLaunchService: ILaunchService,
    private val coroutine: CoroutineContext,
    application: Application,
) : AndroidViewModel(application) {

    private val _deviceUIState: MutableState<DeviceUIState> =
        mutableStateOf(getDeviceType(application))
    val deviceUIState: State<DeviceUIState> = _deviceUIState

    private var _timeFormat = MutableStateFlow(TimeFormat.Base12)
    private fun editTimeFormat(it: Boolean) = viewModelScope.launch(coroutine) {
        _timeFormat.value = (if (it) TimeFormat.Base12 else TimeFormat.Base24)
        this@TimeViewModel.updateTime()
    }

    private val _timeUIState = mutableStateOf<TimeUIState?>(null)
    val timeUIState: State<TimeUIState?> = _timeUIState

    fun updateTime() = viewModelScope.launch(coroutine) {
        val timeFormat = _timeFormat.value
        _timeUIState.value = iTimeDataService.getCurrentTime(timeFormat)
    }

    private val _currentDateFlow: MutableStateFlow<String> = MutableStateFlow("")
    fun updateDate() = viewModelScope.launch(coroutine) {
        val dateFormatPattern = dateFormatUIState.stateIn(this).value
        _currentDateFlow.emit(iTimeDataService.getCurrentDate(dateFormatPattern))
    }

    private fun updateDate(dateFormatPattern: String) = viewModelScope.launch(coroutine) {
        _currentDateFlow.emit(iTimeDataService.getCurrentDate(dateFormatPattern))
    }

    val dateUIStateFlow: Flow<DateUIState> =
        _currentDateFlow.combine(iTimeFormatRecordService.dateFlow) { currentDate, showOrHide ->
            DateUIState(
                showOrHide = showOrHide,
                currentDate = currentDate,
            )
        }

    fun updateDateDisplayOrNot() = viewModelScope.launch(coroutine) {
        val isDateDisplay = dateUIStateFlow.stateIn(this).value
        iTimeFormatRecordService.updateDateRecord(isDateDisplay.showOrHide xor true)
    }


    val timeFormatRecordDataStoreFlow = iTimeFormatRecordService.timeFormatFlow.map {
        this.editTimeFormat(it)
        it
    }

    fun updateTimeFormat() = viewModelScope.launch(coroutine) {
        val timeFormat = timeFormatRecordDataStoreFlow.stateIn(this).value
        iTimeFormatRecordService.updateTimeFormat(timeFormat xor true)
    }


    val dateFormatUIState: Flow<String> = iDateFormatService.dateFormatFlow.map {
        this.updateDate(it)
        it
    }

    fun updateDateFormat(dateFormat: String) = viewModelScope.launch(coroutine) {
        iDateFormatService.updateThemeRecord(dateFormat)
    }

    val powerByShowOrHideStoreFlow: Flow<Boolean> = iLaunchService.powerByShowOrHideStateFlow

    fun updatePowerByShowOrHide() = viewModelScope.launch(coroutine) {
        iLaunchService.updatePowerByShowOrHide(!powerByShowOrHideStoreFlow.stateIn(viewModelScope).value)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}