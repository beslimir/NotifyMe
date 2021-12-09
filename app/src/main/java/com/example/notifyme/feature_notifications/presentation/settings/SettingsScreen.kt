package com.example.notifyme.feature_notifications.presentation.settings

import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notifyme.feature_notifications.presentation.Screen
import com.example.notifyme.feature_notifications.presentation.notifications.NotificationEvent
import com.example.notifyme.feature_notifications.presentation.settings.components.SettingsListItem
import com.example.notifyme.feature_notifications.presentation.settings.components.SettingsListItemDataClass
import com.example.notifyme.feature_notifications.presentation.settings.view_model.SettingsViewModel
import java.util.*

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel(),
    context: Context
) {
    //Settings list objects
    val object1 = SettingsListItemDataClass("Time management", "Set at what time you want the notifications to appear")
    val object2 = SettingsListItemDataClass("Statement", "Click here to accept and read the statement")
    val object3 = SettingsListItemDataClass("Temporary 2", "Temporary text 2")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.h6
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //TODO: Temporary
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.Default.Android,
                        contentDescription = "Android"
                    )
                }
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "School"
                    )
                }
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(listOf(object1, object2, object3)) { listItem ->
                SettingsListItem(
                    listItem = listItem,
                    viewModel = viewModel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (listItem.title == "Time management") {
                                showTimePicker(context, viewModel)
                            } else if (listItem.title == "Statement") {
                                navController.navigate(Screen.StatementScreen.route)
                            } else {
                                //TODO: Temporary...
                                viewModel.onEvent(SettingsEvent.GetNotificationTime)
                            }
                        }
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

fun showTimePicker(context: Context, viewModel: SettingsViewModel) {
    val c = Calendar.getInstance()
    val hour = c.get(Calendar.HOUR_OF_DAY)
    val minute = c.get(Calendar.MINUTE)

    val timePickerDialog = TimePickerDialog(
        context,
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minuteOfHour ->
            viewModel.onEvent(SettingsEvent.SaveNotificationTime("$hourOfDay:$minuteOfHour"))
            Log.d("aaaa", "values: $hourOfDay:$minuteOfHour")
        }, hour, minute, true
    )

    timePickerDialog.show()
}