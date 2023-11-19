package com.example.deadline.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.deadline.databinding.DialogCalendarBinding
import com.example.deadline.viewmodels.SharedViewModel
import java.util.Calendar

class CalendarDialogFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: DialogCalendarBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendarView = binding.calendarView
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
//            onDateSelected(calendar.timeInMillis)
        }
    }

//    private fun onDateSelected(date: Long) {
//        sharedViewModel.selectedDeadlineDate.value = date
//    }
}