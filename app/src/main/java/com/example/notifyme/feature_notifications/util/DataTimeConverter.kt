package com.example.notifyme.feature_notifications.util

import com.example.notifyme.feature_notifications.presentation.countdown.CountdownDataClass
import com.example.notifyme.feature_notifications.presentation.countdown.CountdownObjects
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

    fun calculateRemainingTimeFromDateTime(dateTime: String): List<CountdownDataClass> {
        val sdf = SimpleDateFormat("dd.MM.yyyy. HH:mm", Locale.GERMAN)
        val currentDateAndTime = sdf.format(Date())

        val dateTimeFormat = SimpleDateFormat("dd.MM.yyyy. HH:mm", Locale.GERMAN)
        val endDate = dateTimeFormat.parse(dateTime)!!
        val startDate = dateTimeFormat.parse(currentDateAndTime)

        //display type 1
        val diff: Long = endDate.time - startDate!!.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val weeks = days / 7

        //display type 2
        val weeks2 = weeks
        val days2 = days - weeks * 7
        val hours2 = hours - days * 24
        val minutes2 = minutes - hours * 60

        val obj1 = CountdownDataClass(CountdownObjects.Weeks, weeks2)
        val obj2 = CountdownDataClass(CountdownObjects.Days, days2)
        val obj3 = CountdownDataClass(CountdownObjects.Hours, hours2)
        val obj4 = CountdownDataClass(CountdownObjects.Minutes, minutes2)

        return listOf(obj1, obj2, obj3, obj4)
    }


}