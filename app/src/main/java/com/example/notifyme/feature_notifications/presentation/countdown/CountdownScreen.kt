package com.example.notifyme.feature_notifications.presentation.countdown

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.notifyme.R
import com.example.notifyme.feature_notifications.presentation.settings.SettingsEvent
import com.example.notifyme.feature_notifications.util.DataTimeConverter
import com.example.notifyme.feature_notifications.util.DataTimeConverter.calculateWeeksFromDateTime
import java.util.*

@Composable
fun CountdownScreen(
    context: Context,
) {
    var time by remember { mutableStateOf("11:00") }
    var date by remember { mutableStateOf("25.12.2021.") }
    calculateWeeksFromDateTime("25.12.2021. 11:00")

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
                text = stringResource(R.string.settings_item_4),
                style = MaterialTheme.typography.h6
            )
            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Default.Timer,
                    contentDescription = stringResource(R.string.settings_item_4),
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.countdown_text),
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            //Open the DatePickerDialog
                            val c = Calendar.getInstance()
                            val year = c.get(Calendar.YEAR)
                            val month = c.get(Calendar.MONTH)
                            val dayOfMonth = c.get(Calendar.DAY_OF_MONTH)

                            val datePickerDialog = DatePickerDialog(
                                context,
                                DatePickerDialog.OnDateSetListener { view, _year, _month, _day ->
                                    date = "$_day.${_month + 1}.$_year."
                                }, year, month, dayOfMonth
                            )
                            datePickerDialog.show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = stringResource(R.string.settings_item_4),
                        )
                    }
                    Text(text = date)
                }
                Row(
                    modifier = Modifier
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            //Open the TimePickerDialog
                            val c = Calendar.getInstance()
                            val hour = c.get(Calendar.HOUR_OF_DAY)
                            val minute = c.get(Calendar.MINUTE)

                            val timePickerDialog = TimePickerDialog(
                                context,
                                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minuteOfHour ->
                                    time = DataTimeConverter.formatTimeString(hourOfDay, minuteOfHour)
                                }, hour, minute, true
                            )
                            timePickerDialog.show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = stringResource(R.string.settings_item_4),
                        )
                    }
                    Text(text = time)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(text = "Weeks until the event: 20")
                Text(text = "Days until the event: ${20 * 7}")
                Text(text = "Hours until the event: ${20 * 7 * 24}")
                Text(text = "Minutes until the event: ${20 * 7 * 24 * 60}")
                Text(text = "Seconds until the event: ${20 * 7 * 24 * 60 * 60}")
            }
        }


    }
}














