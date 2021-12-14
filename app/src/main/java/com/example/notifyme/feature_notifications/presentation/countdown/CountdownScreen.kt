package com.example.notifyme.feature_notifications.presentation.countdown

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.*
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notifyme.R
import com.example.notifyme.feature_notifications.presentation.countdown.components.CountdownSection
import com.example.notifyme.feature_notifications.presentation.countdown.view_model.CountdownViewModel
import com.example.notifyme.feature_notifications.util.DataTimeConverter
import java.util.*

@ExperimentalAnimationApi
@Composable
fun CountdownScreen(
    context: Context,
    viewModel: CountdownViewModel = hiltViewModel()
) {
    var time by remember { mutableStateOf(context.getString(R.string.click_on_icon)) }
    var date by remember { mutableStateOf(context.getString(R.string.click_on_icon)) }
    var countdownObjectsList: List<CountdownDataClass> = viewModel.calculateRemainingTime("25.12.2021. 11:00")

    val state = viewModel.state.value

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
                                    viewModel.onEvent(CountdownEvent.DateSelected)
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
                                    time =
                                        DataTimeConverter.formatTimeString(hourOfDay, minuteOfHour)
                                    viewModel.onEvent(CountdownEvent.TimeSelected)
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

            if (state.isDateSelected && state.isTimeSelected) {
                countdownObjectsList = viewModel.calculateRemainingTime("$date $time")
            }

            Spacer(modifier = Modifier.height(32.dp))

            AnimatedVisibility(
                visible = state.isDateSelected && state.isTimeSelected,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                CountdownSection(countdownObjectsList)
            }


        }


    }
}














