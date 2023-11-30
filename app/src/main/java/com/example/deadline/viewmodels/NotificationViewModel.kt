package com.example.deadline.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.deadline.data.NotificationTime

class NotificationViewModel : ViewModel() {
    private val _notificationCounter = MutableLiveData<Int>()
    var notificationCounter: LiveData<Int> = _notificationCounter

    private val _clickedNotifications = MutableLiveData<List<NotificationTime>>()
    var clickedNotifications: LiveData<List<NotificationTime>> = _clickedNotifications

    init {
        _notificationCounter.value = 0
        _clickedNotifications.value = emptyList()
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

    fun updateClickedNotification(notification: NotificationTime) {
        val currentList = _clickedNotifications.value ?: emptyList()

        if (notification.isClicked) {
            if (notification !in currentList) {
                _clickedNotifications.value = currentList + notification
                incrementNotificationCounter()
            }
        } else {
            if (notification in currentList) {
                _clickedNotifications.value = currentList - notification
                decrementNotificationCounter()
            }
        }
    }

}