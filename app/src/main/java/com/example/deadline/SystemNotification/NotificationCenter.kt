package com.example.deadline.SystemNotification

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.deadline.data.NotificationTime
import java.time.LocalDateTime

class NotificationCenter {
    companion object {
        val CHANNEL_ID = "channel_id_example_01"
        fun scheduleNotification(
            context: Context,
            activity: Activity,
            notificationTime: NotificationTime,
            deadline: LocalDateTime
        ) {
            createNotificationChannel(activity)

            var builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Deadline: Assignment#5")
                .setContentText("Deadline is in 3 days")
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText("Deadline is in 3 days")
                )
                .setWhen(System.currentTimeMillis() + 3000)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define.
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        1
                    )
                    return
                }
                notify(1, builder.build())
            }
        }


        private fun createNotificationChannel(activity: Activity) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is not in the Support Library.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel =
                    NotificationChannel(CHANNEL_ID, "channel_name_example_01", importance).apply {
                        description = "description_example_01"
                    }
                // Register the channel with the system.
                val notificationManager: NotificationManager =
                    activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}