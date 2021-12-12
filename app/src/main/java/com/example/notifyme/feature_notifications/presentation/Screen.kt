package com.example.notifyme.feature_notifications.presentation

sealed class Screen(val route: String) {
    object NotificationsScreen: Screen("notifications_screen")
    object NotificationDetailsScreen: Screen("notification_details_screen")
    object SettingsScreen: Screen("settings_screen")
    object StatementScreen: Screen("statement_screen")
    object CreditsScreen: Screen("credits_screen")
    object CountdownScreen: Screen("countdown_screen")
}