package com.apollo.timeflow.module.homefeed.service.dependency

import java.util.Calendar
import java.util.Date

interface IDateModule {
    fun fetchCalendar(): Calendar

    fun fetchDate(): Date
}