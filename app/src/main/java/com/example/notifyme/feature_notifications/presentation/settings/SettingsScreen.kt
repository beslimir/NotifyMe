package com.example.notifyme.feature_notifications.presentation.settings

import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
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
import com.example.notifyme.feature_notifications.presentation.settings.view_model.SettingsViewModel
import java.util.*

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel(),
    context: Context
) {

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(listOf("Time management", "Temporary 1", "Temporary 2")) { listItem ->
            SettingsListItem(
                listItem = listItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (listItem == "Time management") {
                            showTimePicker(context)
                        } else {
                            Toast.makeText(context,"Will be implemented soon...",Toast.LENGTH_SHORT).show()
                        }
                    }
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

fun showTimePicker(context: Context) {
    val c = Calendar.getInstance()
    val hour = c.get(Calendar.HOUR_OF_DAY)
    val minute = c.get(Calendar.MINUTE)

    val timePickerDialog = TimePickerDialog(
        context,
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minuteOfHour ->
            Log.d("aaaa", "values: $hourOfDay:$minuteOfHour")
        }, hour, minute, true
    )

    timePickerDialog.show()
}