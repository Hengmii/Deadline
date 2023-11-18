package com.example.deadline.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DeadlineDao {
    @Query("SELECT * FROM deadline")
    fun getAll(): LiveData<List<Deadline>>

    @Insert
    fun insertDeadline(vararg deadlines: Deadline)
}