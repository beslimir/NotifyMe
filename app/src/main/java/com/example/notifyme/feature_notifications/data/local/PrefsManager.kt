package com.example.notifyme.feature_notifications.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import javax.inject.Singleton

@Singleton
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
            val id = getNextItemId + 1
            preferences[NEXT_ID] = id
        }
    }

    val getNextItemId = runBlocking {
        context.dataStore.data.first()
    }[NEXT_ID] ?: 0

    suspend fun saveLastItemId(size: Int) {
        context.dataStore.edit { preferences ->
            preferences[LAST_ID] = size
        }
    }

    val getLastItemId = runBlocking {
        context.dataStore.data.first()
    }[LAST_ID] ?: 0

    suspend fun setOpenedFlag() {
        context.dataStore.edit { preferences ->
            preferences[FLAG_OPENED] = 1
        }
    }

    val getFlagOpen = runBlocking {
        context.dataStore.data.first()
    }[FLAG_OPENED] ?: 0
}