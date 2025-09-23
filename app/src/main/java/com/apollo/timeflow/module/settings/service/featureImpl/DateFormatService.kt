package com.apollo.timeflow.module.settings.service.featureImpl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.apollo.timeflow.module.settings.service.feature.IDateFormatService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

private val Context.dateFormatDataStore: DataStore<Preferences> by preferencesDataStore(name = "Date Format Record DataStore")

class DateFormatService @Inject constructor(
    @ApplicationContext private val context: Context,
): IDateFormatService {
    override val dateFormatFlow: Flow<String> = this.context.dateFormatDataStore.data.map { preferences ->
        preferences[DATE_FORMAT_RECORD_DATA_STORE_KEY] ?: "MM.dd.yyyy"
    }

    override suspend fun updateThemeRecord(dateFormat: String): Unit = withContext(Dispatchers.IO) {
        context.dateFormatDataStore.edit { preferences ->
            preferences[DATE_FORMAT_RECORD_DATA_STORE_KEY] = dateFormat
        }
    }

    companion object {
        val DATE_FORMAT_RECORD_DATA_STORE_KEY = stringPreferencesKey("date format data store key")
    }
}