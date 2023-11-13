package com.example.deadline.viewmodels

import com.example.deadline.data.database.Deadline
import com.example.deadline.data.database.DeadlineDao

class ProjectDeadlineViewModel(private val deadlineDao: DeadlineDao) {
    fun fullDeadline(): List<Deadline> {
        return deadlineDao.getAll()
    }
}