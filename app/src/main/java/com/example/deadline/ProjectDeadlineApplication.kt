package com.example.deadline

import android.app.Application
import com.example.deadline.data.database.AppDatabase

class ProjectDeadlineApplication : Application() {
    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }
}