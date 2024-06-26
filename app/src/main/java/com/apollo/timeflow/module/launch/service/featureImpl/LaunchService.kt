package com.apollo.timeflow.module.launch.service.featureImpl

import com.apollo.timeflow.module.homefeed.service.dependency.IDateModule
import com.apollo.timeflow.module.launch.service.feature.DayOfWeek
import com.apollo.timeflow.module.launch.service.feature.ILaunchService
import com.apollo.timeflow.module.launch.service.feature.TimeStage
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LaunchService @Inject constructor(
    private val _coroutines: CoroutineContext,
    private val _iDateModule: IDateModule,
) : ILaunchService {
    override suspend fun fetchTimeStage(): TimeStage = withContext(_coroutines) {
        when (_iDateModule.fetchCalendar().get(Calendar.HOUR_OF_DAY)) {
            // 6:00 to 11:59
            in (6..11) -> TimeStage.Morning()
            // 12:00 to 17:59
            in (12..17) -> TimeStage.Afternoon()
            // 18:00 to 19:59
            in (18..19) -> TimeStage.Nightfall()
            // 20:00 to 23:59
            in (20..23) -> TimeStage.Evening()
            // 00:00 to 05:59
            else -> TimeStage.Midnight()
        }
    }

    override suspend fun fetchDayOfWeek(): DayOfWeek = withContext(_coroutines) {
        when (_iDateModule.fetchCalendar().get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> DayOfWeek.Sunday()
            Calendar.MONDAY -> DayOfWeek.Monday()
            Calendar.TUESDAY -> DayOfWeek.Tuesday()
            Calendar.WEDNESDAY -> DayOfWeek.Wednesday()
            Calendar.THURSDAY -> DayOfWeek.Thursday()
            Calendar.FRIDAY -> DayOfWeek.Friday()
            Calendar.SATURDAY -> DayOfWeek.Saturday()
            else -> throw Exception("UNKNOWN TYPE")
        }
    }
}