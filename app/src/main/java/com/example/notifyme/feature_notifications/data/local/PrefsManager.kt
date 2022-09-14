package com.example.notifyme.feature_notifications.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.notifyme.feature_notifications.util.Constants
import kotlinx.coroutines.flow.first
import javax.inject.Singleton

@Singleton
class PrefsManager(
    private val context: Context
) {
    val Context.dataStore by preferencesDataStore("app_preferences")

    companion object {
        val NOTIFICATION_TIME = longPreferencesKey("notification_time")
        val START_DATE = stringPreferencesKey("start_date")
        val END_DATE = stringPreferencesKey("end_date")
        val STATEMENT_ACCEPTED = booleanPreferencesKey("statement_accepted")
        val JSON_AS_STRING = stringPreferencesKey("json_as_string")
    }

    /** Save/Get start & end date **/

    suspend fun saveStartDate(startDate: String) {
        context.dataStore.edit { preferences ->
            preferences[START_DATE] = startDate
        }
    }

    suspend fun getStartDate(): String {
        return context.dataStore.data.first()[START_DATE] ?: Constants.START_DATE
    }

    suspend fun saveEndDate(endDate: String) {
        context.dataStore.edit { preferences ->
            preferences[END_DATE] = endDate
        }
    }

    suspend fun getEndDate(): String {
        return context.dataStore.data.first()[END_DATE] ?: Constants.END_DATE
    }

    /** Notification time **/

    suspend fun saveNotificationTime(millis: Long) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATION_TIME] = millis
        }
    }

    suspend fun getNotificationTime(): Long {
        return context.dataStore.data.first()[NOTIFICATION_TIME] ?: 0
    }

    /** Statement **/

    suspend fun saveStatement() {
        context.dataStore.edit { preferences ->
            preferences[STATEMENT_ACCEPTED] = true
        }
    }

    suspend fun getStatement(): Boolean {
        return context.dataStore.data.first()[STATEMENT_ACCEPTED] ?: false
    }

    /** Json as string **/

    suspend fun saveJson(json: String) {
        context.dataStore.edit { preferences ->
            preferences[JSON_AS_STRING] = json
        }
    }

    suspend fun getJson(): String {
        return context.dataStore.data.first()[JSON_AS_STRING] ?: ""
    }

//    //not returning new value after exit of app (back button) and returning
//    val getNextItemId by lazy {
//        runBlocking {
//            getNextItemId()
//        }
//    }
//
//    //not returning new value after exit of app (back button) and returning
//    val getLastItemId by lazy {
//        runBlocking {
//            context.dataStore.data.first()
//        }[LAST_ID] ?: 0
//    }
}