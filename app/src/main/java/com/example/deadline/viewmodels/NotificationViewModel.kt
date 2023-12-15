package com.example.deadline.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.deadline.view.NotificationList.Notification
import com.example.deadline.data.NotificationTime

class NotificationViewModel : ViewModel() {
    private val _notificationCounter = MutableLiveData<Int>()
    var notificationCounter: MutableLiveData<Int> = _notificationCounter

    private val _selectedNotifications = MutableLiveData<List<NotificationTime>>()
    var selectedNotifications: MutableLiveData<List<NotificationTime>> = _selectedNotifications

    private val _allNotifications = MutableLiveData(NotificationTime.values().toList().sortedByDescending { it.offset }.map { Notification(it, false) })
    var allNotifications: MutableLiveData<List<Notification>> = _allNotifications

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
            if (notification.notificationTime !in currentList) {
                _selectedNotifications.value = currentList + notification.notificationTime
                incrementNotificationCounter()
            }
        } else {
            if (notification.notificationTime in currentList) {
                _selectedNotifications.value = currentList - notification.notificationTime
                decrementNotificationCounter()
            }
        }
    }

    fun updateStates(notificationTimes: List<NotificationTime>) {
        selectedNotifications.value = notificationTimes
        notificationCounter.value = notificationTimes.size
        allNotifications.value = NotificationTime.values().map { item ->
            if (notificationTimes.contains(item)) {
                Notification(item, true)
            } else {
                Notification(item, false)
            }
        }
    }

    fun updateStatesFromStringList(notificationTimesString: List<String>) {
        val notificationTimes = mutableListOf<NotificationTime>()
        for (notification in notificationTimesString) {
            notificationTimes.add(NotificationTime.valueOf(notification))
        }
        updateStates(notificationTimes)
    }
    fun updateStates(notifications: String) {
        val notifications = notifications.split(",")
        val notificationTimes = mutableListOf<NotificationTime>()
    }

}