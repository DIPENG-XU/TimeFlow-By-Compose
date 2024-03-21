package com.apollo.timeflow.bycompose.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollo.timeflow.bycompose.Device
import com.apollo.timeflow.bycompose.TimeFormat
import com.apollo.timeflow.bycompose.service.TimeDataService
import com.apollo.timeflow.bycompose.service.TimeFormatRecordDataStoreService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private var _hourLeftNumberState = mutableIntStateOf(0)
    val hourLeftNumberState: State<Int> = _hourLeftNumberState

    private suspend fun editHourLeft(it: Int) = withContext(Dispatchers.IO) {
        _hourLeftNumberState.intValue = it
    }

    private var _hourRightNumberState = mutableIntStateOf(0)
    val hourRightNumberState: State<Int> = _hourRightNumberState

    private suspend fun editHourRight(it: Int) = withContext(Dispatchers.IO) {
        _hourRightNumberState.intValue = it
    }

    private var _minuteLeftNumberState = mutableIntStateOf(0)
    val minuteLeftNumberState: State<Int> = _minuteLeftNumberState
    private suspend fun editMinuteLeft(it: Int) = withContext(Dispatchers.IO) {
        _minuteLeftNumberState.intValue = it
    }

    private var _minuteRightNumberState = mutableIntStateOf(0)
    val minuteRightNumberState: State<Int> = _minuteRightNumberState
    private suspend fun editMinuteRight(it: Int) = withContext(Dispatchers.IO) {
        _minuteRightNumberState.intValue = it
    }

    private val _amOrPm = mutableStateOf<Int?>(null)
    val amOrPm: State<Int?> = _amOrPm

    fun updateTime() {
        viewModelScope.launch(Dispatchers.IO) {
            val timeFormat = _timeFormat.value ?: TimeFormat.Base12

            val hourLeft: Int =
                if (timeDataService.getCurrentTime(timeFormat)[0] < 10) 0 else timeDataService.getCurrentTime(
                    timeFormat
                )[0].toString()[0].digitToInt()
            val hourRight: Int =
                if (timeDataService.getCurrentTime(timeFormat)[0] < 10) timeDataService.getCurrentTime(
                    timeFormat
                )[0] else timeDataService.getCurrentTime(
                    timeFormat
                )[0].toString()[1].digitToInt()
            val minuteLeft: Int =
                if (timeDataService.getCurrentTime(timeFormat)[1] < 10) 0 else timeDataService.getCurrentTime(
                    timeFormat
                )[1].toString()[0].digitToInt()
            val minuteRight: Int =
                if (timeDataService.getCurrentTime(timeFormat)[1] < 10) timeDataService.getCurrentTime(
                    timeFormat
                )[1] else timeDataService.getCurrentTime(
                    timeFormat
                )[1].toString()[1].digitToInt()

            assert(hourLeft < 10 && hourRight < 10 && minuteLeft < 10 && minuteRight < 10)
            assert(hourLeft >= 0 && hourRight >= 0 && minuteLeft >= 0 && minuteRight >= 0)
            _amOrPm.value = timeDataService.amOrPm()
            editHourLeft(hourLeft)
            editHourRight(hourRight)
            editMinuteLeft(minuteLeft)
            editMinuteRight(minuteRight)
        }
    }

    private val _currentDate = MutableStateFlow("")
    val currentDate: Flow<String> = _currentDate

    fun updateDate() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentDate.emit(timeDataService.getCurrentDate())
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