package com.example.notifyme.feature_notifications.domain.model

data class NotificationItem(
    val content: String,
    val details: List<NotificationDetails>,
    val id: Int,
    val title: String
)