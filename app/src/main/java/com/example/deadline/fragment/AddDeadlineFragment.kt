package com.example.deadline.fragment

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.deadline.R
import com.example.deadline.data.DeadlineState
import com.example.deadline.data.database.AppDatabase
import com.example.deadline.data.database.Deadline
import com.example.deadline.databinding.AddDeadlineFragmentBinding
import com.example.deadline.viewmodels.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.navigation.fragment.findNavController

class AddDeadlineFragment : Fragment() {

    private var _binding: AddDeadlineFragmentBinding? = null

    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by viewModels()

    private var selectedDeadlineDate: Long? = null

    private var selectedStartDate: Long? = null

    private var selectedStartTime: Long? = null

    private var selectedDeadlineTime: Long? = null

    private var selectedColor: String? = null

    private var selectedNotification: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddDeadlineFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.selectedDeadlineDate.observe(viewLifecycleOwner) { date ->
            Log.d("AddDeadlineFragment", "Selected Deadline Date123: ${date}")
            selectedDeadlineDate = date
        }

        sharedViewModel.selectedStartDate.observe(viewLifecycleOwner) { date ->
            Log.d("AddDeadlineFragment", "Selected Start Date124: ${date}")
            selectedStartDate = date
        }

        sharedViewModel.selectedStartTime.observe(viewLifecycleOwner) { time ->
            Log.d("AddDeadlineFragment", "Selected Start Time: ${time}")
            selectedStartTime = time
        }

        sharedViewModel.selectedDeadlineTime.observe(viewLifecycleOwner) { time ->
            Log.d("AddDeadlineFragment", "Selected Deadline Time: ${time}")
            selectedDeadlineTime = time
        }

        sharedViewModel.selectedColor.observe(viewLifecycleOwner) { color ->
            Log.d("AddDeadlineFragment", "Selected Color: ${color}")
            selectedColor = color
        }

        sharedViewModel.selectedNotification.observe(viewLifecycleOwner) { notification ->
            Log.d("AddDeadlineFragment", "Selected Notification: ${notification}")
            selectedNotification = notification
        }

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
            showTimePicker("Start")
        }

        val showDeadlineTimeButton = binding.showDeadlineTimeButton
        showDeadlineTimeButton.setOnClickListener {
            showTimePicker("Deadline")
        }

        val addNotificationButton = binding.addNotificationButton
        addNotificationButton.setOnClickListener {
            val action =
                AddDeadlineFragmentDirections.actionAddDeadlineFragmentToNotificationFragment()
            findNavController().navigate(action)
        }

        val confirmAddDeadlineButton = binding.confirmAddDeadlineButton

        confirmAddDeadlineButton.setOnClickListener {
            val deadlineTitle = binding.deadlineNameInput.text.toString()

            val deadlineInstance = Deadline(
                title = deadlineTitle,
                start = selectedStartDate.toString(),
                startTime = selectedStartTime.toString(),
                deadline = selectedDeadlineDate.toString(),
                deadlineTime = selectedDeadlineTime.toString(),
                color = selectedColor.toString(),
                notification = selectedNotification.toString(),
                state = DeadlineState.TODO.toString(),
            )
            Log.d("AddDeadlineFragment", "Deadline Instance: ${deadlineInstance}")
            CoroutineScope(Dispatchers.IO).launch {
                insertDeadlineIntoDatabase(deadlineInstance)
            }
            navigateToDeadlineRecycleViewFragment()
        }
    }

    private fun showTimePicker(timeType: String) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog =
            TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
                val selectedTime = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, selectedHour)
                    set(Calendar.MINUTE, selectedMinute)
                }
                if (timeType == "Start") {
                    sharedViewModel.selectedStartTime.value = selectedTime.timeInMillis
                    binding.showStartTimeButton.text = SimpleDateFormat(
                        "HH:mm",
                        Locale.getDefault()
                    ).format(selectedTime.timeInMillis)
                } else {
                    sharedViewModel.selectedDeadlineTime.value = selectedTime.timeInMillis
                    binding.showDeadlineTimeButton.text = SimpleDateFormat(
                        "HH:mm",
                        Locale.getDefault()
                    ).format(selectedTime.timeInMillis)
                }
            }, hour, minute, true)
        timePickerDialog.show()
    }


    private fun showCalendarDialog(calenderType: String) {
        val calendarViewLayout = layoutInflater.inflate(R.layout.dialog_calendar, null)
        val calendarView = calendarViewLayout.findViewById<CalendarView>(R.id.calendarView)
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            calendarView.date = selectedDate.timeInMillis
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setView(calendarViewLayout)
            .setTitle("Select ${calenderType} Date")
            .setPositiveButton("OK") { dialog, which ->
                val selectedDate = calendarView.date
                if (calenderType == "Deadline") {
                    sharedViewModel.selectedDeadlineDate.value = selectedDate
                    Log.d("AddDeadlineFragment", "Selected Deadline Date: ${selectedDate}")
                    binding.showDeadlineCalendarButton.text = SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault()
                    ).format(selectedDate)
                   Log.d(SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault()
                    ).format(selectedDate), "Selected Deadline Date: ${selectedDate}")
                } else {
                    sharedViewModel.selectedStartDate.value = selectedDate
                    binding.showStartCalendarButton.text = SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault()
                    ).format(selectedDate)
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun insertDeadlineIntoDatabase(deadline: Deadline) {
        lifecycleScope.launch(Dispatchers.IO) {
            val database = AppDatabase.getDatabase(requireContext())
            val deadlineDao = database.deadlineDao()

            deadlineDao.insertDeadline(deadline)
        }
    }

    private fun navigateToDeadlineRecycleViewFragment() {
        val action =
            AddDeadlineFragmentDirections.actionAddDeadlineFragmentToDeadlineRecycleViewFragment()
        findNavController().navigate(action)
    }
}