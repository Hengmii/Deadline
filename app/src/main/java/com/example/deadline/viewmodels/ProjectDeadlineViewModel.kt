package com.example.deadline.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.deadline.data.database.Deadline
import com.example.deadline.data.database.DeadlineDao

class ProjectDeadlineViewModel(private val deadlineDao: DeadlineDao) : ViewModel() {
    val fullDeadlines : LiveData<List<Deadline>> = deadlineDao.getAll()


    fun updateDeadlineState(id: Int?, state: String) {
        deadlineDao.updateDeadlineState(id, state)
    }

    fun updateDeadline(id: Int?, title: String, start: String, startTime: String, deadline :String, deadlineTime: String, color: String, notification: String) {
        deadlineDao.updateDeadline(id, title, start, startTime, deadline, deadlineTime, color, notification)
    }
}