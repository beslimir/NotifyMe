package com.example.notifyme.feature_notifications.presentation.notifications.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.example.notifyme.R
import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import com.example.notifyme.feature_notifications.util.Constants.ICON_TYPE_FIRE
import com.example.notifyme.feature_notifications.util.Constants.ICON_TYPE_FUN
import com.example.notifyme.feature_notifications.util.Constants.ICON_TYPE_HEART
import com.example.notifyme.feature_notifications.util.Constants.ICON_TYPE_QUESTION

@Composable
fun NotificationListItem(
    notificationItem: NotificationItem,
    modifier: Modifier = Modifier,
//    onItemClick: (NotificationItem) -> Unit
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 25.dp
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

        IconButton(
            onClick = { },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            val painter: Painter?
            val drawableResource: Int
            val tint: Color?
            when (notificationItem.details[0].icon_type) {
                ICON_TYPE_HEART -> {
                    drawableResource = R.drawable.ic_heart
                }
                ICON_TYPE_FIRE -> {
                    drawableResource = R.drawable.ic_fire
                }
                ICON_TYPE_QUESTION -> {
                    drawableResource = R.drawable.ic_bulb
                }
                ICON_TYPE_FUN -> {
                    drawableResource = R.drawable.ic_smiley
                }
                else -> {
                    drawableResource = R.drawable.ic_flower
                }
            }
            painter = painterResource(id = drawableResource)
            tint = Color.DarkGray.copy(alpha = 0.25f)
            Icon(
                painter = painter,
                contentDescription = notificationItem.details[0].icon_type,
                tint = tint
            )
        }
    }
}