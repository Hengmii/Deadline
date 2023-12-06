package com.example.deadline.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DeadlineDao {
    @Query("SELECT * FROM deadline WHERE state = 'TODO'")
    fun getAll(): LiveData<List<Deadline>>

    @Query("SELECT * FROM deadline WHERE state = 'TODO' limit 4")
    fun getFour(): List<Deadline>

    @Query("SELECT * FROM deadline WHERE id = :id")
    fun getDeadline(id: Int?): Deadline

    @Insert
    fun insertDeadline(vararg deadlines: Deadline)

    @Query("UPDATE deadline SET state = :state WHERE id = :id")
    fun updateDeadlineState(id: Int?, state: String)

    @Query("UPDATE deadline SET title = :title, start = :start, startTime = :startTime, deadline = :deadline, deadlineTime = :deadlineTime, color = :color, notification = :notification WHERE id = :id")
    fun updateDeadline(id: Int?, title: String, start: String, startTime: String, deadline: String, deadlineTime: String, color: String, notification: String)

}