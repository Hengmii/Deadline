package com.example.deadline.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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