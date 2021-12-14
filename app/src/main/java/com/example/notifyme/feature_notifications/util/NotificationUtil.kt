package com.example.notifyme.feature_notifications.util

import android.app.AlarmManager
import android.app.Application
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notifyme.BaseApplication
import com.example.notifyme.R
import com.example.notifyme.feature_notifications.broadcasts.TimerBroadcast
import com.example.notifyme.feature_notifications.data.local.PrefsManager
import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import com.example.notifyme.feature_notifications.domain.use_cases.UseCasesWrapper
import com.example.notifyme.feature_notifications.presentation.TemporaryActivity
import java.util.*

data class NotificationUtil(
    private val prefsManager: PrefsManager,
    private val alarmManager: AlarmManager,
    private val application: Application,
    private val useCases: UseCasesWrapper
) {

    suspend fun prepareNotificationForNextItem() {
        val notificationTimeLong = prefsManager.getNotificationTime()
        val notificationTimeString = DataTimeConverter.convertMillisToTime(notificationTimeLong)
        val notificationHours = notificationTimeString.substringBefore(":").toInt()
        val notificationMinutes = notificationTimeString.substringAfter(":").toInt()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, notificationHours)
        calendar.set(Calendar.MINUTE, notificationMinutes)
        calendar.set(Calendar.SECOND, 0)

        //if it's already too late, wait for tomorrow :)
        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        val date: Long = DataTimeConverter.getTodayDateMillisFormat() + prefsManager.getNotificationTime()
        val notificationItem: NotificationItem = useCases.getNotificationByDateUseCase(date)
        val title: String = notificationItem.title

        val intent = Intent(application, TimerBroadcast::class.java).apply {
            putExtra("nextItemDate", date)
            putExtra("nextItemTitle", title)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            application,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }

    /** Push Notification functions **/

    fun sendNotificationNow() {
        val myIntent = Intent(application, TemporaryActivity::class.java)
        myIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(
            application,
            0,
            myIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat.Builder(application, BaseApplication.MY_CHANNEL)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_baseline_notification_important_24)
            .setContentTitle("Content title")
            .setContentText("Content Text")
            .setPriority(Notification.DEFAULT_SOUND)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(application)
        notificationManager.notify(100, builder.build())
    }
}