package com.example.deadline.data

import android.app.Notification
import androidx.compose.ui.graphics.Color
import java.util.Date

enum class DeadlineState {
    TODO,
    DONE,
}

data class Deadline(
    val id: Long,
    val title: String,
    val start: Date,
    val deadline: Date,
    val color: Color,
    val notification: Notification,
    val state: DeadlineState,
)
