package com.apollo.timeflow.bycompose.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apollo.timeflow.bycompose.utils.TimeFormat
import com.apollo.timeflow.bycompose.service.TimeDataService
import com.apollo.timeflow.bycompose.service.TimeFormatRecordDataStoreService
import com.apollo.timeflow.bycompose.uistate.DateUIState
import com.apollo.timeflow.bycompose.uistate.TimeUIState
import com.apollo.timeflow.bycompose.utils.DeviceUIState
import com.apollo.timeflow.bycompose.utils.getDeviceType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimeViewModel @Inject constructor(
    private val timeFormatRecordService: TimeFormatRecordDataStoreService,
    private val timeDataService: TimeDataService,
    private val application: Application,
) : AndroidViewModel(application) {

    private val _deviceUIState: MutableState<DeviceUIState> =
        mutableStateOf(DeviceUIState.Phone())
    val deviceUIState: State<DeviceUIState>
        get() {
            _deviceUIState.value = getDeviceType(application)
            return _deviceUIState
        }

    private var _timeFormat = MutableLiveData(TimeFormat.Base12)
    private fun editTimeFormat(it: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            _timeFormat.value = (if (it) TimeFormat.Base12 else TimeFormat.Base24)
            this@TimeViewModel.updateTime()
        }
    }

    private val _timeUIState = mutableStateOf<TimeUIState?>(null)
    val timeUIState: State<TimeUIState?> = _timeUIState

    fun updateTime() {
        viewModelScope.launch(Dispatchers.IO) {
            val timeFormat = _timeFormat.value ?: TimeFormat.Base12
            val (hours, minutes) = timeDataService.getCurrentTime(timeFormat)

            val hourLeft: Int = if (hours < 10) 0 else hours.toString()[0].digitToInt()
            val hourRight: Int = if (hours < 10) hours else hours.toString()[1].digitToInt()
            val minuteLeft: Int = if (minutes < 10) 0 else minutes.toString()[0].digitToInt()
            val minuteRight: Int = if (minutes < 10) minutes else minutes.toString()[1].digitToInt()
            val amOrPm = timeDataService.amOrPm()

            _timeUIState.value = TimeUIState(
                hourLeft,
                hourRight,
                minuteLeft,
                minuteRight,
                amOrPm,
            )
        }
    }

    private val _currentDateFlow: MutableStateFlow<String> = MutableStateFlow("")
    fun updateDate() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentDateFlow.emit(timeDataService.getCurrentDate())
        }
    }

    val dateUIStateFlow: Flow<DateUIState> =
        _currentDateFlow.combine(timeFormatRecordService.dateFlow) { currentDate, showOrHide ->
            DateUIState(
                showOrHide = showOrHide,
                currentDate = currentDate,
            )
        }

    fun updateDateRecord(value: Boolean) {
        viewModelScope.launch {
            timeFormatRecordService.updateDateRecord(value)
        }
    }

    val timeFormatRecordDataStoreFlow = timeFormatRecordService.timeFormatFlow.map {
        this.editTimeFormat(it)
        it
    }

    fun updateTimeFormat(value: Boolean) {
        viewModelScope.launch {
            timeFormatRecordService.updateTimeFormat(value)
        }
    }
}