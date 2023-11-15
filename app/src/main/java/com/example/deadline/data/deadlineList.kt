package com.example.deadline.data

import android.app.Notification
import androidx.compose.ui.graphics.Color
import java.util.Calendar
import java.util.Date


// Returns initial list of deadlines.
// TODO: Replace with real deadlines with deadlines from the database.

fun deadlineList(): List<Deadline> {
    val calendar = Calendar.getInstance()
    calendar.time = Date()  // 设置当前时间
    calendar.add(Calendar.HOUR_OF_DAY, 24)  // 增加24小时
    return listOf(
        Deadline(
            id = 1,
            title = "Deadline 1",
            start = Date(),
            deadline = calendar.time,
            color = Color(0xFFBB86FC),
            notification = Notification(),
            state = DeadlineState.TODO,
        ),
        Deadline(
            id = 2,
            title = "Deadline 2",
            start = Date(),
            deadline = calendar.time,
            color = Color(0xFFBB86FC),
            notification = Notification(),
            state = DeadlineState.TODO,
        ),
        Deadline(
            id = 3,
            title = "Deadline 3",
            start = Date(),
            deadline = calendar.time,
            color = Color(0xFFBB86FC),
            notification = Notification(),
            state = DeadlineState.TODO,
        ),
        Deadline(
            id = 4,
            title = "Deadline 4",
            start = Date(),
            deadline = calendar.time,
            color = Color(0xFFBB86FC),
            notification = Notification(),
            state = DeadlineState.TODO,
        ),
        Deadline(
            id = 5,
            title = "Deadline 5",
            start = Date(),
            deadline = calendar.time,
            color = Color(0xFFBB86FC),
            notification = Notification(),
            state = DeadlineState.TODO,
        ),
        Deadline(
            id = 6,
            title = "Deadline 6",
            start = Date(),
            deadline = calendar.time,
            color = Color(0xFFBB86FC),
            notification = Notification(),
            state = DeadlineState.TODO,
        ),
        Deadline(
            id = 7,
            title = "Deadline 7",
            start = Date(),
            deadline = calendar.time,
            color = Color(0xFFBB86FC),
            notification = Notification(),
            state = DeadlineState.TODO,
        ),
        Deadline(
            id = 8,
            title = "Deadline 8",
            start = Date(),
            deadline = calendar.time,
            color = Color(0xFFBB86FC),
            notification = Notification(),
            state = DeadlineState.TODO,
        ),
        Deadline(
            id = 9,
            title = "Deadline 9",
            start = Date(),
            deadline = calendar.time,
            color = Color(0xFFBB86FC),
            notification = Notification(),
            state = DeadlineState.DONE,
        ),
        Deadline(
            id = 10,
            title = "Deadline 10",
            start = Date(),
            deadline = calendar.time,
            color = Color(0xFFBB86FC),
            notification = Notification(),
            state = DeadlineState.DONE,
        ),
        Deadline(
            id = 11,
            title = "Deadline 11",
            start = Date(),
            deadline = calendar.time,
            color = Color(0xFFBB86FC),
            notification = Notification(),
            state = DeadlineState.DONE,
        ),
    )

}