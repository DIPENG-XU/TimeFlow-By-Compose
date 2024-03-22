package com.apollo.timeflow.bycompose.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollo.timeflow.bycompose.Device
import com.apollo.timeflow.bycompose.TimeFormat
import com.apollo.timeflow.bycompose.service.TimeDataService
import com.apollo.timeflow.bycompose.service.TimeFormatRecordDataStoreService
import com.apollo.timeflow.bycompose.uistate.TimeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimeViewModel @Inject constructor(
    private val timeFormatRecordService: TimeFormatRecordDataStoreService,
    private val timeDataService: TimeDataService,
) : ViewModel() {

    private val _deviceType: MutableState<Device> = mutableStateOf(Device.Phone())
    val deviceType: State<Device> = _deviceType
    fun notifyDeviceType(device: Device) {
        _deviceType.value = device
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

    private val _currentDate = mutableStateOf("")
    val currentDate: State<String> = _currentDate

    fun updateDate() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentDate.value = timeDataService.getCurrentDate()
        }
    }

    val isDateShowDataStoreFlow = timeFormatRecordService.isDateShow
    fun isDateShow(value: Boolean) {
        viewModelScope.launch {
            timeFormatRecordService.isDateShow(value)
        }
    }

    val timeFormatRecordDataStoreFlow = timeFormatRecordService.timeFormatRecord.map {
        this.editTimeFormat(it)
        it
    }

    fun timeFormatRecordUpdate(value: Boolean) {
        viewModelScope.launch {
            timeFormatRecordService.timeFormat(value)
        }
    }
}