package com.apollo.timeflow.bycompose.service

import com.apollo.timeflow.bycompose.R
import com.apollo.timeflow.bycompose.utils.TimeFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScheduler
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Calendar
import java.util.Date

class TimeDataServiceTest {

    private lateinit var amDate: Date
    private lateinit var pmDate: Date

    private lateinit var amService: TimeDataService
    private lateinit var pmService: TimeDataService

    private val scheduler = TestCoroutineScheduler()
    private val coroutineScope = CoroutineScope(scheduler)

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

            },
            coroutineScope = coroutineScope,
        )
        pmService = TimeDataService(
            iFetchTimeData = object : IFetchTimeData {
                override fun fetchCalendar(): Calendar = Calendar.getInstance().apply {
                    this.time = pmDate
                }

                override fun fetchDate(): Date = pmDate
            },
            coroutineScope = coroutineScope,
        )
    }

    @Test
    fun getCurrentTime() = runBlocking(scheduler) {
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
    fun amOrPm() = runBlocking(scheduler) {
        val am = amService.amOrPm()
        val pm = pmService.amOrPm()

        assertEquals(am, R.string.am)
        assertEquals(pm, R.string.pm)
    }

    @Test
    fun getCurrentDate() = runBlocking(scheduler) {
        val amCurrentDate = amService.getCurrentDate()
        val pmCurrentDate = pmService.getCurrentDate()

        assertEquals(amCurrentDate, "01.10.2024")
        assertEquals(pmCurrentDate, "01.09.2024")
    }
}