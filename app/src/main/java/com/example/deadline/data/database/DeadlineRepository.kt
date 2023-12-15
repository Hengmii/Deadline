package com.example.deadline.data.database


class DeadlineRepository(private val deadlineDao: DeadlineDao) {
    fun getAll() = deadlineDao.getAll()

    suspend fun update(deadline: Deadline) {
        deadlineDao.updateDeadline(
            deadline.id,
            deadline.title,
            deadline.start,
            deadline.startTime,
            deadline.deadline,
            deadline.deadlineTime,
            deadline.color,
            deadline.notification
        )
    }

    suspend fun updateState(id: Int?, state: String) {
        deadlineDao.updateDeadlineState(id, state)
    }

    suspend fun insert(deadline: Deadline) {
        deadlineDao.insertDeadline(deadline)
    }

}