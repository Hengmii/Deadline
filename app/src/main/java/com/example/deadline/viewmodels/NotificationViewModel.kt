package com.example.deadline.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationViewModel : ViewModel() {
    private val _notificationCounter = MutableLiveData<Int>()
    var notificationCounter: LiveData<Int> = _notificationCounter

    init {
        _notificationCounter.value = 0
    }

    fun incrementNotificationCounter() {
        val currentValue = _notificationCounter.value ?: 0
        _notificationCounter.value = currentValue + 1
    }

    fun decrementNotificationCounter() {
        val currentValue = _notificationCounter.value ?: 0
        if (currentValue > 0) {
            _notificationCounter.value = currentValue - 1
        }
    }

    fun resetNotificationCounter() {
        _notificationCounter.value = 0
    }
}