package com.example.deadline.data.database

import androidx.room.TypeConverter
import java.util.Date

object Converters {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}