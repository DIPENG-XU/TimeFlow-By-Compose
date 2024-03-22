package com.apollo.timeflow.bycompose.service

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TimeFormatRecordDataStoreService @Inject constructor(
    @ApplicationContext val context: Context,
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Time Unit Record DataStore")

    val isDateShow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_DATE_SHOW] ?: false
        }

    suspend fun isDateShow(value: Boolean) {
        context.dataStore.edit { settings ->
            settings[IS_DATE_SHOW] = value
        }
    }

    val timeFormatRecord: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[TIME_FORMAT_RECORD_TAG] ?: false
        }

    suspend fun timeFormat(value: Boolean) {
        context.dataStore.edit { settings ->
            settings[TIME_FORMAT_RECORD_TAG] = value
        }
    }

    companion object {
        val IS_DATE_SHOW = booleanPreferencesKey("is date show")
        val TIME_FORMAT_RECORD_TAG = booleanPreferencesKey("time format")
    }
}