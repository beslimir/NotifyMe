package com.example.notifyme.feature_notifications.presentation.notifications.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.notifyme.feature_notifications.domain.model.NotificationItem

@Composable
fun NotificationListItem(
    notificationItem: NotificationItem,
    onItemClick: (NotificationItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(notificationItem) }
            .padding(20.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "${notificationItem.id}. ${notificationItem.title}",
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Ellipsis
        )
    }
}