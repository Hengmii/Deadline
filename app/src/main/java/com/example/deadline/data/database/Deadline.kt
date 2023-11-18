package com.example.deadline.data.database

import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity
@TypeConverters(Converters::class)
data class Deadline (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val start: String,
    val startTime: String,
    val deadline: String,
    val deadlineTime: String,
    val color: String,
    val notification: String,
    val state: String
)