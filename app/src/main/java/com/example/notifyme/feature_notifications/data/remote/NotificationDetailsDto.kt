package com.example.notifyme.feature_notifications.data.remote

import com.example.notifyme.feature_notifications.domain.model.NotificationDetails

data class NotificationDetailsDto(
    val author: String,
    val dedicated_to: String,
    val icon_type: String,
    val type: String
) {
    fun toNotificationDetails(): NotificationDetails {
        return NotificationDetails(
            author = author,
            type = type,
            dedicated_to = dedicated_to,
            icon_type = icon_type
        )
    }
}