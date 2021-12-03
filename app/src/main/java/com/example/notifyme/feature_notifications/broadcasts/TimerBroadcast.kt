package com.example.notifyme.feature_notifications.broadcasts

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notifyme.BaseApplication.Companion.MY_CHANNEL
import com.example.notifyme.R
import com.example.notifyme.feature_notifications.presentation.TemporaryActivity

class TimerBroadcast: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val id: Int? = intent?.getIntExtra("nextItemId", 1)
        val title: String? = intent?.getStringExtra("nextItemTitle")

        val myIntent = Intent(context, TemporaryActivity::class.java)
        myIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(context!!, MY_CHANNEL)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_baseline_notification_important_24)
            .setContentTitle("Content title id: $title")
            .setContentText("Content Text: $id")
            .setPriority(Notification.DEFAULT_SOUND)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(100, builder.build())
    }
}