package com.example.notifyme.feature_notifications.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class UserPreferences(
    context: Context
) {
    private val applicationContext = context.applicationContext
    private val Context.dataStore by preferencesDataStore("app_preferences")

    companion object {
        val NEXT_ID = intPreferencesKey("next_id")
        val LAST_ID = intPreferencesKey("last_id")
    }

    suspend fun incrementNextItemId() {
        applicationContext.dataStore.edit { preferences ->
            var id = 0
            nextItemId.collect {
                id = it + 1
            }
            preferences[NEXT_ID] = id
        }
    }

    val nextItemId: Flow<Int>
        get() = applicationContext.dataStore.data.map { preferences ->
            preferences[NEXT_ID] ?: 0
        }

    suspend fun saveLastItemId(size: Int) {
        applicationContext.dataStore.edit { preferences ->
            preferences[LAST_ID] = size
        }
    }

    val lastItemId: Flow<Int>
        get() = applicationContext.dataStore.data.map { preferences ->
            preferences[LAST_ID] ?: 0
        }
}