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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notifyme.R
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
                    context.getString(R.string.settings_item_1) -> {
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
                    context.getString(R.string.settings_item_2) -> {
                        navController.navigate(Screen.StatementScreen.route)
                    }
                    context.getString(R.string.settings_item_3) -> {
                        navController.navigate(Screen.CreditsScreen.route)
                    }
                    context.getString(R.string.settings_item_4) -> {
                        navController.navigate(Screen.CountdownScreen.route)
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
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
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
                            imageVector = listItem.icon,
                            contentDescription = "Time"
                        )
                    }
                    if (listItem.title == context.getString(R.string.settings_item_1)) {
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
