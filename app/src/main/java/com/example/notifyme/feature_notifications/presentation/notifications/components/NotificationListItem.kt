package com.example.notifyme.feature_notifications.presentation.notifications.components

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.example.notifyme.feature_notifications.domain.model.NotificationItem

@Composable
fun NotificationListItem(
    notificationItem: NotificationItem,
    modifier: Modifier = Modifier,
//    onItemClick: (NotificationItem) -> Unit
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp
) {
    Box(
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(notificationItem.color),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                drawRoundRect(
                    color = Color(
                        ColorUtils.blendARGB(notificationItem.color, 0x000000, 0.2f)
                    ),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp)
        ) {
            Text(
                text = notificationItem.title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = notificationItem.content,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
        }
//        IconButton(
//            onClick = onDeleteClick,
//            modifier = Modifier.align(Alignment.BottomEnd)
//        ) {
//            Icon(
//                imageVector = Icons.Default.Delete,
//                contentDescription = "Delete notification")
//        }
    }





//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { onItemClick(notificationItem) }
//            .padding(20.dp),
//        horizontalArrangement = Arrangement.Start
//    ) {
//        Text(
//            text = "${notificationItem.id}. ${notificationItem.title}",
//            style = MaterialTheme.typography.body1,
//            overflow = TextOverflow.Ellipsis
//        )
//    }
}