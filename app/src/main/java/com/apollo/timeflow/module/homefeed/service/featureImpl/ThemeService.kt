package com.apollo.timeflow.module.homefeed.service.featureImpl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.apollo.timeflow.module.homefeed.service.feature.IThemeService
import com.apollo.timeflow.module.settings.utils.FontMappingType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

private val Context.themeDataStore: DataStore<Preferences> by preferencesDataStore(name = "Theme Record DataStore")

private val Context.fontDataStore: DataStore<Preferences> by preferencesDataStore(name = "Font Record DataStore")

class ThemeService @Inject constructor(
    private val coroutineScope: CoroutineScope,
    @ApplicationContext private val context: Context,
) : IThemeService {
    override val themeFlow: Flow<Int> = this.context.themeDataStore.data.map { preferences ->
        preferences[THEME_DATA_STORE_KEY] ?: 1
    }

    override suspend fun updateThemeRecord(value: Int): Unit =
        withContext(coroutineScope.coroutineContext) {
            context.themeDataStore.edit { preferences ->
                preferences[THEME_DATA_STORE_KEY] = value
            }
        }

    override val fontFlow: Flow<String> = this.context.fontDataStore.data.map { preferences ->
        preferences[FONT_DATA_STORE_KEY] ?: FontMappingType.PoppinsBold.name
    }

    override suspend fun updateFont(fontName: String): Unit =
        withContext(coroutineScope.coroutineContext) {
            context.fontDataStore.edit { preferences ->
                preferences[FONT_DATA_STORE_KEY] = fontName
            }
        }

    companion object {
        val THEME_DATA_STORE_KEY = intPreferencesKey("theme data store key")

        val FONT_DATA_STORE_KEY = stringPreferencesKey("font data store key")
    }
}