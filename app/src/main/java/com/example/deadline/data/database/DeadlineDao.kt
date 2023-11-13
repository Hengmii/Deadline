package com.example.deadline.data.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface DeadlineDao {
    @Query("SELECT * FROM deadline")
    fun getAll(): List<Deadline>
}