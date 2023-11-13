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
    @PrimaryKey val id: Int,
    val title: String,
    val start: String,
    val deadline: String,
    val color: String,
    val notification: String,
    val state: String
)