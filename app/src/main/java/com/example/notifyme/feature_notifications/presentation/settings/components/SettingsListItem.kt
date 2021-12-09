package com.example.notifyme.feature_notifications.presentation.settings.components

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notifyme.feature_notifications.presentation.Screen
import com.example.notifyme.feature_notifications.presentation.settings.SettingsEvent
import com.example.notifyme.feature_notifications.presentation.settings.view_model.SettingsViewModel
import com.example.notifyme.feature_notifications.util.DataTimeConverter
import java.util.*

@Composable
fun SettingsListItem(
    navController: NavController,
    context: Context,
    listItem: SettingsListItemDataClass,
    viewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) {
    var time by remember { mutableStateOf(viewModel.getNotificationTimeAsString()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .clickable {
                when (listItem.title) {
                    "Time management" -> {
                        //Open the TimePickerDialog
                        val c = Calendar.getInstance()
                        val hour = c.get(Calendar.HOUR_OF_DAY)
                        val minute = c.get(Calendar.MINUTE)

                        val timePickerDialog = TimePickerDialog(
                            context,
                            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minuteOfHour ->
                                viewModel.onEvent(SettingsEvent.SaveNotificationTime("$hourOfDay:$minuteOfHour"))
                                time = DataTimeConverter.formatTimeString(hourOfDay, minuteOfHour)
                            }, hour, minute, true
                        )
                        timePickerDialog.show()
                    }
                    "Statement" -> {
                        navController.navigate(Screen.StatementScreen.route)
                    }
                    else -> {
                        //TODO: Temporary...
                        viewModel.onEvent(SettingsEvent.GetNotificationTime)
                    }
                }
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier.weight(4f),
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
                            text = time,
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
