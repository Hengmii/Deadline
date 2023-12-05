package com.example.deadline.NotificationList

import com.example.deadline.data.NotificationTime

class Notification {
    var isClicked: Boolean
    var notificationTime: NotificationTime

    constructor(notificationTime: NotificationTime, isClicked: Boolean) {
        this.notificationTime = notificationTime
        this.isClicked = isClicked
    }


    fun toggleClicked() {
        isClicked = !isClicked
    }
}