package com.example.deadline.fragment

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import androidx.compose.ui.res.dimensionResource
import androidx.fragment.app.Fragment
import com.example.deadline.R
import java.util.Calendar

class AddDeadlineFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_deadline_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val showDeadlineCalendarButton = view.findViewById<Button>(R.id.showDeadlineCalendarButton)
        showDeadlineCalendarButton.setOnClickListener {
            showCalendarDialog("Deadline")
        }

        val showStartCalendarButton = view.findViewById<Button>(R.id.showStartCalendarButton)
        showStartCalendarButton.setOnClickListener {
            showCalendarDialog("Start")
        }

        val showStartTimeButton = view.findViewById<Button>(R.id.showStartTimeButton)
        showStartTimeButton.setOnClickListener {
            showTimePicker()
        }

        val showDeadlineTimeButton = view.findViewById<Button>(R.id.showDeadlineTimeButton)
        showDeadlineTimeButton.setOnClickListener {
            showTimePicker()
        }

//        val addNotificationButton = view.findViewById<Button>(R.id.add_notification_button)
//        addNotificationButton.setOnClickListener {
//            showNotificationDialog()
//        }
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog =
            TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
            }, hour, minute, true)

        timePickerDialog.show()
    }


    private fun showCalendarDialog(calenderType: String) {
        val calendarViewLayout = layoutInflater.inflate(R.layout.dialog_calendar, null)
        val calendarView = calendarViewLayout.findViewById<CalendarView>(R.id.calendarView)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(calendarViewLayout)
            .setTitle("Select ${calenderType} Date")
            .setPositiveButton("OK") { dialog, which ->
                val selectedDate = calendarView.date
                Log.d("CalendarDialogFragment", "Selected Date: ${selectedDate}")
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

}