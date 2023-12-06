package com.example.deadline.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.deadline.data.database.Deadline
import com.example.deadline.databinding.FullDetailFragmentBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class DetailDeadlineFragment : Fragment() {
    private var _binding: FullDetailFragmentBinding? = null

    private val binding get() = _binding!!

    private lateinit var deadline: Deadline

    private val handler = Handler(Looper.getMainLooper())

    private val updateRunnable = object : Runnable {
        override fun run() {
            updateLeftTime()
            handler.postDelayed(this, 1000)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FullDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun updateLeftTime() {
        var duration = deadline.deadlineTime.toLong() - System.currentTimeMillis()

        val days = TimeUnit.MILLISECONDS.toDays(duration)
        duration -= java.util.concurrent.TimeUnit.DAYS.toMillis(days)

        val hours = TimeUnit.MILLISECONDS.toHours(duration)
        duration -= java.util.concurrent.TimeUnit.HOURS.toMillis(hours)

        val minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
        duration -= java.util.concurrent.TimeUnit.MINUTES.toMillis(minutes)

        val seconds = TimeUnit.MILLISECONDS.toSeconds(duration)
        duration -= java.util.concurrent.TimeUnit.SECONDS.toMillis(seconds)

//        if (days > 0) {
//            binding.actualTimeLeft.text = "$days days, $hours hours, $minutes minutes, $seconds seconds"
//        } else if (hours > 0) {
//            binding.actualTimeLeft.text = "$hours hours, $minutes minutes, $seconds seconds"
//        } else if (minutes > 0) {
//            binding.actualTimeLeft.text = "$minutes minutes, $seconds seconds"
//        } else if (seconds > 0) {
//            binding.actualTimeLeft.text = "$seconds seconds"
//        } else {
//            binding.actualTimeLeft.text = "The deadline has passed"
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deadline = DetailDeadlineFragmentArgs.fromBundle(requireArguments()).deadline
        handler.post(updateRunnable)

        binding.deadlineTitle.text = deadline.title
        binding.startDate.text = convertTimestampToDate(deadline.startTime.toLong())
        binding.deadlineDate.text = convertTimestampToDate(deadline.deadlineTime.toLong())

        binding.notificationContent.text = deadline.notification

        val confirmButton = binding.confirmReviewDetailButton
        confirmButton.setOnClickListener {
            findNavController().popBackStack()
        }

        val editButton = binding.editDeadlineButton
        editButton.setOnClickListener {
            val action = DetailDeadlineFragmentDirections.actionDetailDeadlineFragmentToEditDeadlineFragment(deadline)
            findNavController().navigate(action)
        }
    }

    private fun convertTimestampToDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val date = Date(timestamp)
        return sdf.format(date)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}