package com.example.notifyme.feature_notifications.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
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
            val id = getNextItemId() + 1
            preferences[NEXT_ID] = id
        }
    }

    suspend fun getNextItemId(): Int {
        return context.dataStore.data.first()[NEXT_ID] ?: 0
    }

    suspend fun saveLastItemId(size: Int) {
        context.dataStore.edit { preferences ->
            preferences[LAST_ID] = size
        }
    }

    suspend fun getLastItemId(): Int {
        return context.dataStore.data.first()[LAST_ID] ?: 0
    }

    suspend fun setOpenedFlag() {
        context.dataStore.edit { preferences ->
            preferences[FLAG_OPENED] = 1
        }
    }

    suspend fun getFlagOpen(): Int {
        return context.dataStore.data.first()[FLAG_OPENED] ?: 0
    }

    //not returning new value after exit of app (back button) and returning
    val getNextItemId by lazy {
        runBlocking {
            getNextItemId()
        }
    }

    //not returning new value after exit of app (back button) and returning
    val getFlagOpen by lazy {
        runBlocking {
            context.dataStore.data.first()
        }[FLAG_OPENED] ?: 0
    }

    //not returning new value after exit of app (back button) and returning
    val getLastItemId by lazy {
        runBlocking {
            context.dataStore.data.first()
        }[LAST_ID] ?: 0
    }
}