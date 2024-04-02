package com.apollo.timeflow.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apollo.timeflow.service.TimeDataService
import com.apollo.timeflow.service.TimeFormatRecordDataStoreService
import com.apollo.timeflow.uistate.DateUIState
import com.apollo.timeflow.uistate.TimeUIState
import com.apollo.timeflow.utils.DeviceUIState
import com.apollo.timeflow.utils.TimeFormat
import com.apollo.timeflow.utils.getDeviceType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimeViewModel @Inject constructor(
    private val timeFormatRecordService: TimeFormatRecordDataStoreService,
    private val timeDataService: TimeDataService,
    application: Application,
) : AndroidViewModel(application) {

    private val _deviceUIState: MutableState<DeviceUIState> =
        mutableStateOf(getDeviceType(application))
    val deviceUIState: State<DeviceUIState> = _deviceUIState

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

    fun updateDateRecord() {
        viewModelScope.launch {
            val isDateDisplay = dateUIStateFlow.stateIn(this).value
            timeFormatRecordService.updateDateRecord(isDateDisplay.showOrHide xor true)
        }
    }

    val timeFormatRecordDataStoreFlow = timeFormatRecordService.timeFormatFlow.map {
        this.editTimeFormat(it)
        it
    }

    fun updateTimeFormat() {
        viewModelScope.launch {
            val timeFormat = timeFormatRecordDataStoreFlow.stateIn(this).value
            timeFormatRecordService.updateTimeFormat(timeFormat xor true)
        }
    }
}