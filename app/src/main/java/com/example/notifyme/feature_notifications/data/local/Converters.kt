package com.example.notifyme.feature_notifications.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.notifyme.feature_notifications.data.util.JsonParser
import com.example.notifyme.feature_notifications.domain.model.NotificationDetails
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromDetailsJson(json: String): List<NotificationDetails> {
        return jsonParser.fromJson<ArrayList<NotificationDetails>>(
            json,
            object: TypeToken<ArrayList<NotificationDetails>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toDetailsJson(details: List<NotificationDetails>): String {
        return jsonParser.toJson(
            details,
            object: TypeToken<ArrayList<NotificationDetails>>(){}.type
        ) ?: "[]"
    }
}