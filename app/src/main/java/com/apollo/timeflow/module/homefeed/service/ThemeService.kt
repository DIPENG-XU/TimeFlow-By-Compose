package com.apollo.timeflow.module.homefeed.service

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

private val Context.themeDataStore: DataStore<Preferences> by preferencesDataStore(name = "Theme Record DataStore")

class ThemeService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val coroutineScope: CoroutineScope,
) {
    val themeFlow: Flow<Int> = this.context.themeDataStore.data.map { preferences ->
        preferences[THEME_DATA_STORE_KEY] ?: 1
    }

    suspend fun updateThemeRecord(value: Int): Unit =
        withContext(coroutineScope.coroutineContext) {
            context.themeDataStore.edit { preferences ->
                preferences[THEME_DATA_STORE_KEY] = value
            }
        }

    companion object {
        val THEME_DATA_STORE_KEY = intPreferencesKey("theme data store key")
    }
}