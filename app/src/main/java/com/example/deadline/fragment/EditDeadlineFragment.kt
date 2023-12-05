package com.example.deadline.fragment

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.deadline.R
import com.example.deadline.data.NotificationTime
import com.example.deadline.databinding.EditDeadlineFragmentBinding
import com.example.deadline.viewmodels.NotificationViewModel
import com.example.deadline.viewmodels.SharedViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import com.example.deadline.NotificationList.Notification
import com.example.deadline.data.DeadlineState
import com.example.deadline.data.database.AppDatabase
import com.example.deadline.data.database.Deadline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EditDeadlineFragment : Fragment() {

    private var _binding: EditDeadlineFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var notificationViewModel: NotificationViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deadline = EditDeadlineFragmentArgs.fromBundle(requireArguments()).deadline

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        notificationViewModel =
            ViewModelProvider(requireActivity()).get(NotificationViewModel::class.java)
        sharedViewModel.deadlineTitle.value = deadline.title
        sharedViewModel.selectedStartDate.value = deadline.start.toLong()
        sharedViewModel.selectedDeadlineDate.value = deadline.deadline.toLong()
        sharedViewModel.selectedColor.value = deadline.color
        sharedViewModel.selectedStartTime.value = deadline.startTime.toLong()
        sharedViewModel.selectedDeadlineTime.value = deadline.deadlineTime.toLong()
        // separate deadline.notifications using ",", and parse each as enum type NotificationTime
        val notifications = deadline.notification.split(",")
        val notificationTimes = mutableListOf<NotificationTime>()
        for (notification in notifications) {
            notificationTimes.add(NotificationTime.valueOf(notification))
        }
        sharedViewModel.selectedNotifications.value = notificationTimes.map { it.toString() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EditDeadlineFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val deadline = EditDeadlineFragmentArgs.fromBundle(requireArguments()).deadline

        binding.deadlineNameInput.text =
            Editable.Factory.getInstance().newEditable(sharedViewModel.deadlineTitle.value)
        binding.showStartCalendarButton.text =
            convertTimestampToDate(sharedViewModel.selectedStartDate.value!!)
        binding.showDeadlineCalendarButton.text =
            convertTimestampToDate(sharedViewModel.selectedDeadlineDate.value!!)
        binding.showStartTimeButton.text =
            convertTimestampToDate(sharedViewModel.selectedStartTime.value!!)
        binding.showDeadlineTimeButton.text =
            convertTimestampToDate(sharedViewModel.selectedDeadlineTime.value!!)

        sharedViewModel.selectedDeadlineDate.observe(viewLifecycleOwner) { date ->
            Log.d("AddDeadlineFragment", "Selected Deadline Date123: ${date}")
            binding.showDeadlineCalendarButton.text = SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
            ).format(date)
        }

        sharedViewModel.selectedStartDate.observe(viewLifecycleOwner) { date ->
            Log.d("AddDeadlineFragment", "Selected Start Date124: ${date}")
            binding.showStartCalendarButton.text = SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
            ).format(date)
        }

        sharedViewModel.selectedStartTime.observe(viewLifecycleOwner) { time ->
            Log.d("AddDeadlineFragment", "Selected Start Time: ${time}")
            binding.showStartTimeButton.text = SimpleDateFormat(
                "HH:mm",
                Locale.getDefault()
            ).format(time)
        }

        sharedViewModel.selectedDeadlineTime.observe(viewLifecycleOwner) { time ->
            Log.d("AddDeadlineFragment", "Selected Deadline Time: ${time}")
            binding.showDeadlineTimeButton.text = SimpleDateFormat(
                "HH:mm",
                Locale.getDefault()
            ).format(time)
        }

        sharedViewModel.selectedNotifications.observe(viewLifecycleOwner) { notification ->
            Log.d("AddDeadlineFragment", "Selected Notification: ${notification}")
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
                EditDeadlineFragmentDirections.actionEditDeadlineFragmentToNotificationFragment()
            findNavController().navigate(action)
            notificationViewModel.updateStatesFromStringList(sharedViewModel.selectedNotifications.value!!)
        }

        val circleDrawable = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setSize(120, 120)
        }
        circleDrawable.setColor(Color.parseColor(sharedViewModel.selectedColor.value))
        binding.previewSelectedColor.background = circleDrawable

        binding.previewSelectedColor.setOnClickListener {
            val action =
                EditDeadlineFragmentDirections.actionEditDeadlineFragmentToSelectColorFragment()
            findNavController().navigate(action)
        }

        sharedViewModel.selectedColor.observe(viewLifecycleOwner) { color ->
            circleDrawable.setColor(Color.parseColor(color))
            binding.previewSelectedColor.background = circleDrawable
        }

        binding.confirmEditDeadlineButton.setOnClickListener {
            if (!checkDataValidity()) {
                val dialog = AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("Please fill in all the fields")
                    .setPositiveButton("OK", null)
                    .create()
                dialog.show()
                return@setOnClickListener
            }
            sharedViewModel.deadlineTitle.value = binding.deadlineNameInput.text.toString()
            sharedViewModel.selectedDeadlineState.value = DeadlineState.TODO.toString()

            val deadlineInstance = Deadline(
                id = deadline.id,
                title = sharedViewModel.deadlineTitle.value.toString(),
                start = sharedViewModel.selectedStartDate.value.toString(),
                startTime = sharedViewModel.selectedStartTime.value.toString(),
                deadline = sharedViewModel.selectedDeadlineDate.value.toString(),
                deadlineTime = sharedViewModel.selectedDeadlineTime.value.toString(),
                color = sharedViewModel.selectedColor.value.toString(),
                notification = sharedViewModel.selectedNotifications.value?.joinToString(",") ?: "",
                state = sharedViewModel.selectedDeadlineState.value.toString()
            )

            CoroutineScope(Dispatchers.IO).launch {
                updateDeadlineInDatabase(deadlineInstance)
            }
            navigateToDeadlineRecycleViewFragment()
        }

    }

    private fun updateDeadlineInDatabase(deadline: Deadline) {
        lifecycleScope.launch(Dispatchers.IO) {
            val database = AppDatabase.getDatabase(requireContext())
            val deadlineDao = database.deadlineDao()

            deadlineDao.updateDeadline(deadline.id, deadline.title, deadline.start, deadline.startTime, deadline.deadline, deadline.deadlineTime, deadline.color, deadline.notification)
        }
    }

    private fun navigateToDeadlineRecycleViewFragment() {
        findNavController().popBackStack()
    }

    private fun checkDataValidity(): Boolean {
        return sharedViewModel.selectedStartDate != null &&
                sharedViewModel.selectedStartTime != null &&
                sharedViewModel.selectedDeadlineDate != null &&
                sharedViewModel.selectedDeadlineTime != null &&
                binding.deadlineNameInput.text.toString() != ""
    }

    private fun convertTimestampToDate(timestamp: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date(timestamp))
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
                    Log.d(
                        SimpleDateFormat(
                            "dd/MM/yyyy",
                            Locale.getDefault()
                        ).format(selectedDate), "Selected Deadline Date: ${selectedDate}"
                    )
                } else {
                    sharedViewModel.selectedStartDate.value = selectedDate
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}