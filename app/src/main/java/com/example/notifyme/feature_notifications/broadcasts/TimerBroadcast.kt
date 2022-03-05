package com.example.notifyme.feature_notifications.broadcasts

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notifyme.BaseApplication.Companion.MY_CHANNEL
import com.example.notifyme.R
import com.example.notifyme.feature_notifications.presentation.MainActivity

class TimerBroadcast : BroadcastReceiver() {

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context?, intent: Intent?) {
        val title: String? = intent?.getStringExtra("notificationTitle")
        val content: String? = intent?.getStringExtra("notificationContent")

        val myIntent = Intent(context, MainActivity::class.java)
        myIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getActivity(context, 0, myIntent, PendingIntent.FLAG_MUTABLE)
            } else {
                PendingIntent.getActivity(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            }
        val builder = NotificationCompat.Builder(context!!, MY_CHANNEL)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_heart_shape)
            .setColor(Color.RED)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.notification_large_picture))
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .setSummaryText(context.resources.getString(R.string.notification_summary_text))
            )
            .setContentTitle("$title")
            .setContentText("$content")
            .setPriority(Notification.DEFAULT_SOUND)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(100, builder.build())
    }
}