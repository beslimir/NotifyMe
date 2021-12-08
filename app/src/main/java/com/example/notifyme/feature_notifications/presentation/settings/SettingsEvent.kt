package com.example.notifyme.feature_notifications.presentation.settings

sealed class SettingsEvent {
    data class SaveNotificationTime(val time: String) : SettingsEvent()
    object GetNotificationTime : SettingsEvent()
}