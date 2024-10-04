package com.apollo.timeflow.module.launch.service.feature

import androidx.annotation.StringRes
import com.apollo.timeflow.R
import kotlinx.coroutines.flow.Flow

sealed class TimeStage(
    @StringRes val stringResource: Int
) {
    override fun hashCode(): Int = stringResource

    override fun equals(other: Any?): Boolean {
        val timeStage = other as? TimeStage ?: return false
        return (timeStage.stringResource == this.stringResource)
    }

    // 6:00 to 11:59
    class Morning: TimeStage(R.string.morning)
    // 12:00 to 17:59
    class Afternoon: TimeStage(R.string.afternoon)
    // 18:00 to 19:59
    class Nightfall: TimeStage(R.string.nightfall)
    // 20:00 to 23:59
    class Midnight: TimeStage(R.string.midnight)
    // 00:00 to 05:59
    class Evening: TimeStage(R.string.evening)
}

sealed class DayOfWeek(
    @StringRes val stringResource: Int
) {
    override fun hashCode(): Int = stringResource

    override fun equals(other: Any?): Boolean {
        val timeStage = other as? DayOfWeek ?: return false
        return (timeStage.stringResource == this.stringResource)
    }

    class Monday: DayOfWeek(R.string.monday)
    class Tuesday: DayOfWeek(R.string.tuesday)
    class Wednesday: DayOfWeek(R.string.wednesday)
    class Thursday: DayOfWeek(R.string.thursday)
    class Friday: DayOfWeek(R.string.friday)
    class Saturday: DayOfWeek(R.string.saturday)
    class Sunday: DayOfWeek(R.string.sunday)
}

interface ISplashService {
    val powerByShowOrHideStateFlow: Flow<Boolean>

    suspend fun updatePowerByShowOrHide(showOrHide: Boolean)

    suspend fun fetchTimeStage(): TimeStage

    suspend fun fetchDayOfWeek(): DayOfWeek

    suspend fun fetchPowerByStringResource(): Int
}