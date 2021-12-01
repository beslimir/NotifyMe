package com.example.notifyme.feature_notifications.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import java.io.IOException

class PrefsManager(
    private val context: Context
) {
    val Context.dataStore by preferencesDataStore("app_preferences")

    companion object {
        val NEXT_ID = intPreferencesKey("next_id")
        val LAST_ID = intPreferencesKey("last_id")
        val FLAG_OPENED = intPreferencesKey("opened_flag")
    }

    suspend fun incrementNextItemId() {
        context.dataStore.edit { preferences ->
            var id = 0
            nextItemId.collect {
                id = it + 1
            }
            preferences[NEXT_ID] = id
        }
    }

    val nextItemId: Flow<Int>
        get() = context.dataStore.data.map { preferences ->
            preferences[NEXT_ID] ?: 0
        }

    suspend fun saveLastItemId(size: Int) {
        context.dataStore.edit { preferences ->
            preferences[LAST_ID] = size
        }
    }

    val lastItemId: Flow<Int>
        get() = context.dataStore.data.map { preferences ->
            preferences[LAST_ID] ?: 0
        }

    suspend fun setOpenedFlag() {
        context.dataStore.edit { preferences ->
            preferences[FLAG_OPENED] = 1
        }
    }

    val openedFlag: Flow<Int>
        get() = context.dataStore.data.catch { exception ->
            if (exception is IOException) {
                Log.d("aaaa", "Error reading preferences: $exception")
                emit(emptyPreferences())
            } else {
                Log.d("aaaa", "Error: $exception")
            }
        }.map { preferences ->
            preferences[FLAG_OPENED] ?: 0
        }

    val getOpenedFlag: Flow<Int> = context.dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[FLAG_OPENED] ?: 0
        }
}