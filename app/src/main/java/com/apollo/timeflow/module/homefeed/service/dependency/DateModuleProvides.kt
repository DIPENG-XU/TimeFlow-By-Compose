package com.apollo.timeflow.module.homefeed.service.dependency

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Calendar
import java.util.Date
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DateModuleProvides {
    @Singleton
    @Provides
    fun providesFetchTimeData(): IDateModule = object : IDateModule {
        override fun fetchCalendar(): Calendar = Calendar.getInstance()

        override fun fetchDate(): Date = Date()
    }
}