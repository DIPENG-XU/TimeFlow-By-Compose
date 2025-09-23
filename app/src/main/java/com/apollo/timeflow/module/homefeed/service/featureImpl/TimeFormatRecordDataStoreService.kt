package com.apollo.timeflow.module.homefeed.service.featureImpl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.apollo.timeflow.module.homefeed.service.feature.ITimeFormatRecordDataStoreService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Time Unit Record DataStore")

class TimeFormatRecordDataStoreService @Inject constructor(
    @ApplicationContext private val context: Context,
): ITimeFormatRecordDataStoreService {

    override val dateFlow: Flow<Boolean> = this.context.dataStore.data.map { preferences ->
        preferences[IS_DATE_DISPLAY] ?: true
    }

    override suspend fun updateDateRecord(isDateDisplay: Boolean): Unit = withContext(Dispatchers.IO) {
        context.dataStore.edit { preferences ->
            preferences[IS_DATE_DISPLAY] = isDateDisplay
        }
    }

    override val timeFormatFlow: Flow<Boolean> = this.context.dataStore.data.map { preferences ->
        preferences[TIME_FORMAT_RECORD_TAG] ?: true
    }

    override suspend fun updateTimeFormat(value: Boolean): Unit = withContext(Dispatchers.IO) {
        context.dataStore.edit { preferences ->
            preferences[TIME_FORMAT_RECORD_TAG] = value
        }
    }

    companion object {
        val IS_DATE_DISPLAY = booleanPreferencesKey("is date display")
        val TIME_FORMAT_RECORD_TAG = booleanPreferencesKey("time format")
    }
}