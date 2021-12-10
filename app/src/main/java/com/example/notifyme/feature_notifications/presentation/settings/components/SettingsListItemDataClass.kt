package com.example.notifyme.feature_notifications.presentation.settings.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.ui.graphics.vector.ImageVector

data class SettingsListItemDataClass(
    val title: String = "Title",
    val description: String = "Description",
    val icon: ImageVector = Icons.Default.Timer
)