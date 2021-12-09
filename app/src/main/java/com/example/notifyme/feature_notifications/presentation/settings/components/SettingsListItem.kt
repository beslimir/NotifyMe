package com.example.notifyme.feature_notifications.presentation.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.notifyme.feature_notifications.presentation.settings.view_model.SettingsViewModel

@Composable
fun SettingsListItem(
    listItem: SettingsListItemDataClass,
    viewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier.weight(3f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = listItem.title,
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = listItem.description,
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.LightGray
                )
            }
            Column(
                Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Column(
                    Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = "Time"
                        )
                    }
                    if (listItem.title == "Time management") {
                        Text(
                            text = viewModel.getNotificationTimeAsString(),
                            style = MaterialTheme.typography.body2,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.LightGray
                        )
                    }
                }
            }
        }
    }
}
