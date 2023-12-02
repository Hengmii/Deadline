package com.example.deadline.fragment

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.compose.ui.res.colorResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
import com.example.deadline.viewmodels.ColorViewModel

class AddDeadlineFragment : Fragment() {

    private var _binding: AddDeadlineFragmentBinding? = null

    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by viewModels()

    private lateinit var colorViewModel: ColorViewModel

    private var selectedDeadlineDate: Long? = null

    private var selectedStartDate: Long? = null

    private var selectedStartTime: Long? = null

    private var selectedDeadlineTime: Long? = null

    private var selectedColor: String? = "#666666"

    private var selectedNotification: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddDeadlineFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        colorViewModel = ViewModelProvider(requireActivity()).get(ColorViewModel::class.java)
        colorViewModel.selectedColor.value = "#666666"

        colorViewModel.selectedColor.observe(this, Observer { color ->
            selectedColor = color
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.selectedDeadlineDate.observe(viewLifecycleOwner) { date ->
            Log.d("AddDeadlineFragment", "Selected Deadline Date123: ${date}")
            selectedDeadlineDate = date
            binding.showDeadlineCalendarButton.text = SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
            ).format(selectedDeadlineDate)
        }

        sharedViewModel.selectedStartDate.observe(viewLifecycleOwner) { date ->
            Log.d("AddDeadlineFragment", "Selected Start Date124: ${date}")
            selectedStartDate = date
            binding.showStartCalendarButton.text = SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
            ).format(selectedStartDate)
        }

        sharedViewModel.selectedStartTime.observe(viewLifecycleOwner) { time ->
            Log.d("AddDeadlineFragment", "Selected Start Time: ${time}")
            selectedStartTime = time
            binding.showStartTimeButton.text = SimpleDateFormat(
                "HH:mm",
                Locale.getDefault()
            ).format(selectedStartTime)
        }

        sharedViewModel.selectedDeadlineTime.observe(viewLifecycleOwner) { time ->
            Log.d("AddDeadlineFragment", "Selected Deadline Time: ${time}")
            selectedDeadlineTime = time
            binding.showDeadlineTimeButton.text = SimpleDateFormat(
                "HH:mm",
                Locale.getDefault()
            ).format(selectedDeadlineTime)
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
            if (!checkDataValidity()) {
                val dialog = AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("Please fill in all the fields")
                    .setPositiveButton("OK", null)
                    .create()
                dialog.show()
                return@setOnClickListener
            }
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

        val previewSelectedColorButton = binding.previewSelectedColor
        val circleDrawable = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setSize(120, 120)
        }
        colorViewModel.selectedColor.observe(viewLifecycleOwner) { color ->
            circleDrawable.setColor(Color.parseColor(color))
            previewSelectedColorButton.background = circleDrawable
        }

        previewSelectedColorButton.setOnClickListener() {
            val action =
                AddDeadlineFragmentDirections.actionAddDeadlineFragmentToSelectColorFragment()
            findNavController().navigate(action)
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
                } else {
                    sharedViewModel.selectedDeadlineTime.value = selectedTime.timeInMillis
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
                   Log.d(SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault()
                    ).format(selectedDate), "Selected Deadline Date: ${selectedDate}")
                } else {
                    sharedViewModel.selectedStartDate.value = selectedDate
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
        findNavController().popBackStack()
    }

    private fun checkDataValidity(): Boolean {
        return selectedStartDate != null && selectedStartTime != null && selectedDeadlineDate != null && selectedDeadlineTime != null && binding.deadlineNameInput.text.toString() != ""
    }
}