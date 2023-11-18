package com.example.deadline.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.deadline.data.database.Deadline
import com.example.deadline.data.database.DeadlineDao

class ProjectDeadlineViewModel(private val deadlineDao: DeadlineDao) : ViewModel() {
    val fullDeadlines : LiveData<List<Deadline>> = deadlineDao.getAll()
}