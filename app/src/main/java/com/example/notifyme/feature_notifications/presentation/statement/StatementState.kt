package com.example.notifyme.feature_notifications.presentation.statement

import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import com.example.notifyme.feature_notifications.domain.util.OrderType

data class StatementState(
    val isStatementOpened: Boolean = false
)