package com.example.deadline.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration
import java.time.LocalDateTime

enum class NotificationTime(val displayText: String, val offset: Duration) {
    @RequiresApi(Build.VERSION_CODES.O)
    AT_DEADLINE("At time of deadline", Duration.ZERO),

    @RequiresApi(Build.VERSION_CODES.O)
    ONE_MONTH_BEFORE("1 month before deadline", Duration.ofDays(30)),

    @RequiresApi(Build.VERSION_CODES.O)
    TWO_WEEKS_BEFORE("2 weeks before deadline", Duration.ofDays(14)),

    @RequiresApi(Build.VERSION_CODES.O)
    ONE_WEEK_BEFORE("1 week before deadline", Duration.ofDays(7)),

    @RequiresApi(Build.VERSION_CODES.O)
    THREE_DAYS_BEFORE("3 days before deadline", Duration.ofDays(3)),

    @RequiresApi(Build.VERSION_CODES.O)
    ONE_DAY_BEFORE("1 day before deadline", Duration.ofDays(1)),

    @RequiresApi(Build.VERSION_CODES.O)
    TWELVE_HOURS_BEFORE("12 hours before deadline", Duration.ofHours(12)),

    @RequiresApi(Build.VERSION_CODES.O)
    SIX_HOURS_BEFORE("6 hours before deadline", Duration.ofHours(6)),

    @RequiresApi(Build.VERSION_CODES.O)
    THREE_HOURS_BEFORE("3 hours before deadline", Duration.ofHours(3)),

    @RequiresApi(Build.VERSION_CODES.O)
    ONE_HOUR_BEFORE("1 hour before deadline", Duration.ofHours(1)),

    @RequiresApi(Build.VERSION_CODES.O)
    THIRTY_MINUTES_BEFORE("30 minutes before deadline", Duration.ofMinutes(30)),

    @RequiresApi(Build.VERSION_CODES.O)
    FIFTEEN_MINUTES_BEFORE("15 minutes before deadline", Duration.ofMinutes(15)),

    @RequiresApi(Build.VERSION_CODES.O)
    FIVE_MINUTES_BEFORE("5 minutes before deadline", Duration.ofMinutes(5)),

    @RequiresApi(Build.VERSION_CODES.O)
    ONE_MINUTE_BEFORE("1 minute before deadline", Duration.ofMinutes(1));

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNotificationTime(deadline: LocalDateTime): LocalDateTime {
        return deadline.minus(offset)
    }

}