package com.example.deadline.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Calendar
import com.example.deadline.data.NotificationTime

@Entity
@TypeConverters(Converters::class)
@Parcelize
data class Deadline(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val start: String,
    val startTime: String,
    val deadline: String,
    val deadlineTime: String,
    val color: String,
    val notification: String,
    var state: String
) : Parcelable

private fun parseTimestampStringToDateTime(timestampString: String): String {
    // as MM/dd/yyyy HH:mm from timestampString (timestamp)
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestampString.toLong()
    val month = calendar.get(Calendar.MONTH) + 1
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val year = calendar.get(Calendar.YEAR)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    return "$month/$day/$year $hour:$minute"
}

fun Deadline.startDateTimeString(): String {
    return parseTimestampStringToDateTime(startTime)
}

fun Deadline.deadlineDateTimeString(): String {
    return parseTimestampStringToDateTime(deadlineTime)
}

fun Deadline.remainDayHourMinuteSecond(): List<Long> {
    var duration = deadlineTime.toLong() - System.currentTimeMillis()
    val days = java.util.concurrent.TimeUnit.MILLISECONDS.toDays(duration)
    duration -= java.util.concurrent.TimeUnit.DAYS.toMillis(days)
    val hours = java.util.concurrent.TimeUnit.MILLISECONDS.toHours(duration)
    duration -= java.util.concurrent.TimeUnit.HOURS.toMillis(hours)
    val minutes = java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(duration)
    duration -= java.util.concurrent.TimeUnit.MINUTES.toMillis(minutes)
    val seconds = java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(duration)
    return listOf(days, hours, minutes, seconds)
}

fun Deadline.notificationTimes(): List<NotificationTime> {
    val notifications = notification.split(",")
    val notificationTimes = mutableListOf<NotificationTime>()
    for (notification in notifications) {
        if (notification == "") {
            continue
        }
        val notificationTime = NotificationTime.valueOf(notification)
        notificationTimes.add(notificationTime)
    }
    return notificationTimes
}
