package com.apollo.timeflow.module.launch.service.featureImpl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.apollo.timeflow.R
import com.apollo.timeflow.module.homefeed.service.dependency.IDateModule
import com.apollo.timeflow.module.launch.service.feature.DayOfWeek
import com.apollo.timeflow.module.launch.service.feature.ILaunchService
import com.apollo.timeflow.module.launch.service.feature.TimeStage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

private val Context.powerByShowOrHide: DataStore<Preferences> by preferencesDataStore(name = "PowerBy Show Or Hide DataStore")

class LaunchService @Inject constructor(
    private val _coroutineScope: CoroutineScope,
    private val _iDateModule: IDateModule,
    @ApplicationContext private val _context: Context,
) : ILaunchService {
    override suspend fun fetchTimeStage(): TimeStage =
        withContext(_coroutineScope.coroutineContext) {
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

    override suspend fun fetchDayOfWeek(): DayOfWeek =
        withContext(_coroutineScope.coroutineContext) {
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

    override suspend fun fetchPowerByStringResource(): Int = R.string.power_by_apollo

    override val powerByShowOrHideStateFlow: Flow<Boolean> =
        _context.powerByShowOrHide.data.map { preferences ->
            preferences[POWER_BY_SHOW_OR_HIDE_KEY] ?: true
        }

    override suspend fun updatePowerByShowOrHide(showOrHide: Boolean): Unit =
        withContext(_coroutineScope.coroutineContext) {
            _context.powerByShowOrHide.edit { preferences ->
                preferences[POWER_BY_SHOW_OR_HIDE_KEY] = showOrHide
            }
        }

    companion object {
        private val POWER_BY_SHOW_OR_HIDE_KEY = booleanPreferencesKey("Power By show or hide key")
    }
}