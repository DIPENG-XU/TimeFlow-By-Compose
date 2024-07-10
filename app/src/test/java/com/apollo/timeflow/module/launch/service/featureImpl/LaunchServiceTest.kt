package com.apollo.timeflow.module.launch.service.featureImpl

import com.apollo.timeflow.R
import com.apollo.timeflow.module.homefeed.service.dependency.IDateModule
import com.apollo.timeflow.module.launch.service.feature.DayOfWeek
import com.apollo.timeflow.module.launch.service.feature.TimeStage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScheduler
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Calendar
import java.util.Date

class LaunchServiceTest {
    private lateinit var date: Date

    private lateinit var launchService: LaunchService

    private val scheduler = TestCoroutineScheduler()
    private val coroutineScope = CoroutineScope(scheduler)

    @Before
    fun setUp() {
        // 1704803400000L
        // 2024-01-09 20:30:00 Tuesday
        date = Date(1704803400000L)

        launchService = LaunchService(
            coroutineScope.coroutineContext,
            object : IDateModule {
                override fun fetchCalendar(): Calendar = Calendar.getInstance().apply {
                    this.time = date
                }

                override fun fetchDate(): Date = date
            }
        )
    }

    @Test
    fun test_fetchTimeStage(): Unit = runBlocking(coroutineScope.coroutineContext) {
        val timeStage = launchService.fetchTimeStage()
        assertEquals(timeStage, TimeStage.Evening())
    }

    @Test
    fun test_fetchDayOfWeek() = runBlocking(coroutineScope.coroutineContext) {
        val dayOfWeek = launchService.fetchDayOfWeek()
        assertEquals(dayOfWeek, DayOfWeek.Tuesday())
    }

    @Test
    fun test_fetchPowerByStringResource() = runBlocking(coroutineScope.coroutineContext) {
        val powerByStringResource = launchService.fetchPowerByStringResource()
        assertEquals(powerByStringResource, R.string.power_by_apollo)
    }
}