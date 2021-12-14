package com.example.notifyme.feature_notifications.util

import android.util.Log
import com.example.notifyme.feature_notifications.util.Constants.TAG
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DataTimeConverter {

    fun convertDateToMillis(date: String): Long {
        val dateMillisFormat = SimpleDateFormat("dd.MM.yyyy.", Locale.GERMAN)
        try {
            val mDate: Date = dateMillisFormat.parse(date)!!
            val timeInMillis: Long = mDate.time

            return timeInMillis
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0
    }

    fun convertMillisToDate(millis: Long): String {
        val dateMillisFormat = SimpleDateFormat("dd.MM.yyyy.", Locale.GERMAN)
        try {
            val c = Calendar.getInstance()
            c.timeInMillis = millis
            val date = dateMillisFormat.format(c.time)

            return date
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return "01.01.1900."
    }

    fun convertTimeToMillis(time: String): Long {
        val hours = time.substringBefore(":").toLong()
        val minutes = time.substringAfter(":").toLong()
        val millis = TimeUnit.HOURS.toMillis(hours) + TimeUnit.MINUTES.toMillis(minutes)

        return millis
    }

    fun convertMillisToTime(millis: Long): String {
        return String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(millis),
            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))
        )
    }

    fun formatTimeString(hourOfDay: Int, minuteOfHour: Int): String {
        var time: String
        if (hourOfDay < 10) {
            time = "0$hourOfDay:$minuteOfHour"
            if (minuteOfHour < 10) {
                time = "0$hourOfDay:0$minuteOfHour"
            }
        } else if (minuteOfHour < 10) {
            time = "$hourOfDay:0$minuteOfHour"
        } else {
            time = "$hourOfDay:$minuteOfHour"
        }

        return time
    }

    fun getTodayDateStringFormat(): String {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        val dateFormat = SimpleDateFormat("dd.MM.yyyy.", Locale.GERMAN)

        return dateFormat.format(calendar.timeInMillis)
    }

    fun getTodayDateMillisFormat(): Long {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        val dateFormat = SimpleDateFormat("dd.MM.yyyy.", Locale.GERMAN)
        val dateString = dateFormat.format(calendar.timeInMillis)

        return convertDateToMillis(dateString)
    }

    fun calculateWeeksFromDateTime(dateTime: String): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy. HH:mm", Locale.GERMAN)
        val currentDateAndTime = sdf.format(Date())

        val dateTimeFormat = SimpleDateFormat("dd.MM.yyyy. HH:mm", Locale.GERMAN)
        val endDate = dateTimeFormat.parse(dateTime)!!
        val startDate = dateTimeFormat.parse(currentDateAndTime)

        //TODO: Temporary...

        val diff: Long = endDate.time - startDate!!.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val weeks = days / 7

        val weeks2 = weeks
        val days2 = days - weeks * 7
        val hours2 = hours - days * 24
        val minutes2 = minutes - hours * 60
        val seconds2 = seconds - minutes * 60

        return "a"
    }


}