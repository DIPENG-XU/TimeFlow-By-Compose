package com.apollo.timeflow.bycompose.service

import org.junit.Assert
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import java.util.Calendar
import java.util.Date
import com.apollo.timeflow.bycompose.R
import com.apollo.timeflow.bycompose.TimeFormat
import kotlinx.coroutines.runBlocking

class TimeDataServiceTest {

    private lateinit var amDate: Date
    private lateinit var pmDate: Date

    private lateinit var amService: TimeDataService
    private lateinit var pmService: TimeDataService


    @Before
    fun setUp() {
        // 1704846600000L
        // 2024-01-10 08:30:00
        amDate = Date(1704846600000L)
        // 1704803400000L
        // 2024-01-09 20:30:00
        pmDate = Date(1704803400000L)
        amService = TimeDataService(
            iFetchTimeData = object : IFetchTimeData {
                override fun fetchCalendar(): Calendar = Calendar.getInstance().apply {
                    this.time = amDate
                }

                override fun fetchDate(): Date = amDate

            }
        )
        pmService = TimeDataService(
            iFetchTimeData = object : IFetchTimeData {
                override fun fetchCalendar(): Calendar = Calendar.getInstance().apply {
                    this.time = pmDate
                }

                override fun fetchDate(): Date = pmDate
            }
        )
    }

    @Test
    fun getCurrentTime() = runBlocking {
        val amBase12 = amService.getCurrentTime(TimeFormat.Base12)
        val amBase24 = amService.getCurrentTime(TimeFormat.Base24)
        val pmBase12 = pmService.getCurrentTime(TimeFormat.Base12)
        val pmBase24 = pmService.getCurrentTime(TimeFormat.Base24)

        assertEquals(amBase12.first, 8)
        assertEquals(amBase12.second, 30)
        assertEquals(amBase24.first, 8)
        assertEquals(amBase24.second, 30)
        assertEquals(pmBase12.first, 8)
        assertEquals(pmBase12.second, 30)
        assertEquals(pmBase24.first, 20)
        assertEquals(pmBase24.second, 30)
    }

    @Test
    fun amOrPm() = runBlocking {
        val am = amService.amOrPm()
        val pm = pmService.amOrPm()

        assertEquals(am, R.string.am)
        assertEquals(pm, R.string.pm)
    }

    @Test
    fun getCurrentDate() = runBlocking {
        val amCurrentDate = amService.getCurrentDate()
        val pmCurrentDate = pmService.getCurrentDate()

        assertEquals(amCurrentDate, "01.10.2024")
        assertEquals(pmCurrentDate, "01.09.2024")
    }
}