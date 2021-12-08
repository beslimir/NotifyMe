package com.example.notifyme.feature_notifications.util

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


}