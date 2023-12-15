package com.example.deadline

import android.app.Application
import com.example.deadline.data.database.AppDatabase
import com.example.deadline.data.database.DeadlineRepository

class ProjectDeadlineApplication : Application() {
    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }
    val repository by lazy { DeadlineRepository(database.deadlineDao()) }
}