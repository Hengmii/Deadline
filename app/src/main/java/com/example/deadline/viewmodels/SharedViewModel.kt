package com.example.deadline.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var deadlineTitle = MutableLiveData<String>()

    val selectedDeadlineDate = MutableLiveData<Long>()

    val selectedStartDate = MutableLiveData<Long>()

    val selectedStartTime = MutableLiveData<Long>()

    val selectedDeadlineTime = MutableLiveData<Long>()

    val selectedNotifications = MutableLiveData<List<String>>()

    var selectedColor = MutableLiveData<String>()

    var selectedDeadlineState = MutableLiveData<String>()
}