package com.apollo.timeflow.bycompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apollo.timeflow.bycompose.service.TimeDataService
import com.apollo.timeflow.bycompose.service.TimeFormatRecordDataStoreService

class MainViewModelProviderFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        with(modelClass) {
            return when {
                isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                    TimeFormatRecordDataStoreService.getInstance(),
                    TimeDataService.getInstance(),
                )

                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            } as T
        }
    }
}