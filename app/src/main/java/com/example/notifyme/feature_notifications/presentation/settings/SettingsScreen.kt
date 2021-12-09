package com.example.notifyme.feature_notifications.presentation.settings

import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
    val object2 = SettingsListItemDataClass("Temporary 1", "Temporary text 1")
    val object3 = SettingsListItemDataClass("Temporary 2", "Temporary text 2")


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