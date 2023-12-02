package com.example.deadline.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.deadline.data.Notification
import com.example.deadline.data.NotificationTime

class NotificationViewModel : ViewModel() {
    private val _notificationCounter = MutableLiveData<Int>()
    var notificationCounter: LiveData<Int> = _notificationCounter

    private val _selectedNotifications = MutableLiveData<List<Notification>>()
    var selectedNotifications: LiveData<List<Notification>> = _selectedNotifications

    private val _allNotifications = NotificationTime.values().toList().sortedByDescending { it.offset }.map { Notification(it, false) }
    var allNotifications: List<Notification> = _allNotifications

    init {
        _notificationCounter.value = 0
        _selectedNotifications.value = emptyList()
    }

    private fun incrementNotificationCounter() {
        val currentValue = _notificationCounter.value ?: 0
        _notificationCounter.value = currentValue + 1
    }

    private fun decrementNotificationCounter() {
        val currentValue = _notificationCounter.value ?: 0
        if (currentValue > 0) {
            _notificationCounter.value = currentValue - 1
        }
    }

    fun resetNotificationCounter() {
        _notificationCounter.value = 0
    }

    fun onClickNotification(notification: Notification) {
        notification.toggleClicked()
        val currentList = _selectedNotifications.value ?: emptyList()

        if (notification.isClicked) {
            if (notification !in currentList) {
                _selectedNotifications.value = currentList + notification
                incrementNotificationCounter()
            }
        } else {
            if (notification in currentList) {
                _selectedNotifications.value = currentList - notification
                decrementNotificationCounter()
            }
        }
    }

}