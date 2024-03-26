package com.apollo.timeflow.bycompose.service

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Time Unit Record DataStore")

class TimeFormatRecordDataStoreService(
    private val coroutineScope: CoroutineScope,
    private val context: Context,
) {
    @Inject
    constructor(@ApplicationContext context: Context, coroutineScope: CoroutineScope) : this(
        coroutineScope,
        context,
    )

    val dateFlow: Flow<Boolean> = this.context.dataStore.data.map { preferences ->
        preferences[TIME_FORMAT_RECORD_TAG] ?: false
    }

    suspend fun updateDateRecord(value: Boolean): Unit = withContext(coroutineScope.coroutineContext) {
        context.dataStore.edit { preferences ->
            preferences[IS_DATE_SHOW] = value
        }
    }

    val timeFormatFlow: Flow<Boolean> = this.context.dataStore.data.map { preferences ->
        preferences[TIME_FORMAT_RECORD_TAG] ?: false
    }

    suspend fun updateTimeFormat(value: Boolean): Unit = withContext(coroutineScope.coroutineContext) {
        context.dataStore.edit { preferences ->
            preferences[TIME_FORMAT_RECORD_TAG] = value
        }
    }

    companion object {
        val IS_DATE_SHOW = booleanPreferencesKey("is date show")
        val TIME_FORMAT_RECORD_TAG = booleanPreferencesKey("time format")
    }
}