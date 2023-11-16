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
import com.example.deadline.databinding.AddDeadlineFragmentBinding
import com.example.deadline.databinding.FragmentDeadlineListRecyclerViewBinding
import java.util.Calendar

class AddDeadlineFragment : Fragment() {

    private var _binding: AddDeadlineFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddDeadlineFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val showDeadlineCalendarButton = binding.showDeadlineCalendarButton
        showDeadlineCalendarButton.setOnClickListener {
            showCalendarDialog("Deadline")
        }

        val showStartCalendarButton = binding.showStartCalendarButton
        showStartCalendarButton.setOnClickListener {
            showCalendarDialog("Start")
        }

        val showStartTimeButton = binding.showStartTimeButton
        showStartTimeButton.setOnClickListener {
            showTimePicker()
        }

        val showDeadlineTimeButton = binding.showDeadlineTimeButton
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