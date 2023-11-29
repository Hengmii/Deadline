package com.example.deadline.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ColorViewModel : ViewModel() {
    private var _selectedColor = MutableLiveData<String>()
    val selectedColor: LiveData<String> = _selectedColor

    fun setSelectedColor(colorData: String) {
        _selectedColor.value = colorData
    }
}