package com.example.deadline.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.deadline.data.database.DeadlineDao

class ProjectDeadlineViewModelFactory(
    private val deadlineDao: DeadlineDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectDeadlineViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProjectDeadlineViewModel(deadlineDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}