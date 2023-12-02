package com.example.deadline.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DeadlineDao {
    @Query("SELECT * FROM deadline WHERE state = 'TODO'")
    fun getAll(): LiveData<List<Deadline>>

    @Insert
    fun insertDeadline(vararg deadlines: Deadline)

    @Query("UPDATE deadline SET state = :state WHERE id = :id")
    fun updateDeadlineState(id: Int?, state: String)

}