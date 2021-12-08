package com.example.notifyme.feature_notifications.presentation

sealed class Screen(val route: String) {
    object NotificationsScreen: Screen("notifications_screen")
    object NotificationDetailsScreen: Screen("notification_details_screen")
    object SettingsScreen: Screen("settings_screen")
}