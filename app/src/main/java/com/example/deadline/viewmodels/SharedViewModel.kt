package com.example.deadline.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val selectedDeadlineDate = MutableLiveData<Long>()

    val selectedStartDate = MutableLiveData<Long>()

    val selectedStartTime = MutableLiveData<Long>()

    val selectedDeadlineTime = MutableLiveData<Long>()

    val selectedColor = MutableLiveData<String>()

    val selectedNotification = MutableLiveData<String>()
}