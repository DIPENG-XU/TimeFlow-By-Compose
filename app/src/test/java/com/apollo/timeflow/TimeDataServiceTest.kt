package com.apollo.timeflow

import com.apollo.timeflow.module.homefeed.service.featureImpl.TimeDataService
import com.apollo.timeflow.module.homefeed.service.dependency.IDateModule
import com.apollo.timeflow.module.homefeed.uistate.TimeUIState
import com.apollo.timeflow.utils.BASE12
import com.apollo.timeflow.utils.BASE24
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
            iDateModule = object : IDateModule {
                override fun fetchCalendar(): Calendar = Calendar.getInstance().apply {
                    this.time = amDate
                }

                override fun fetchDate(): Date = amDate

            },
            coroutineScope = coroutineScope,
        )
        pmService = TimeDataService(
            iDateModule = object : IDateModule {
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
        val amBase12 = amService.getCurrentTime(BASE12)
        val amBase24 = amService.getCurrentTime(BASE24)
        val pmBase12 = pmService.getCurrentTime(BASE12)
        val pmBase24 = pmService.getCurrentTime(BASE24)

        val predictedAmBase12 = TimeUIState(
            hoursLeft = 0,
            hoursRight = 8,
            minutesLeft = 3,
            minutesRight = 0,
            amOrPM = R.string.am
        )
        assertEquals(predictedAmBase12, amBase12)

        val predictedAmBase24 = TimeUIState(
            hoursLeft = 0,
            hoursRight = 8,
            minutesLeft = 3,
            minutesRight = 0,
            amOrPM = R.string.am
        )
        assertEquals(predictedAmBase24, amBase24)

        val predictedPmBase12 = TimeUIState(
            hoursLeft = 0,
            hoursRight = 8,
            minutesLeft = 3,
            minutesRight = 0,
            amOrPM = R.string.pm
        )
        assertEquals(predictedPmBase12, pmBase12)

        val predictedPmBase24 = TimeUIState(
            hoursLeft = 2,
            hoursRight = 0,
            minutesLeft = 3,
            minutesRight = 0,
            amOrPM = R.string.pm
        )
        assertEquals(predictedPmBase24, pmBase24)
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
        val amCurrentDate = amService.getCurrentDate("MM.dd.yyyy")
        val pmCurrentDate = pmService.getCurrentDate("MM.dd.yyyy")

        assertEquals(amCurrentDate, "01.10.2024")
        assertEquals(pmCurrentDate, "01.09.2024")
    }
}