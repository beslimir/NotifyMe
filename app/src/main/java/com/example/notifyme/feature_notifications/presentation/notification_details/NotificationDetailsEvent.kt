package com.example.notifyme.feature_notifications.presentation.notification_details

sealed class NotificationDetailsEvent {
    data class ChangeColor(val color: Int): NotificationDetailsEvent()
    object SaveNotificationItem: NotificationDetailsEvent()
}