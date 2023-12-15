package com.example.deadline.view.NotificationList

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.deadline.SystemNotification.NotificationCenter
import com.example.deadline.data.NotificationTime
import com.example.deadline.databinding.NotificationFragmentBinding
import com.example.deadline.viewmodels.NotificationViewModel
import com.example.deadline.viewmodels.SharedViewModel
import java.time.LocalDateTime


class NotificationFragment : Fragment() {
    private var _binding: NotificationFragmentBinding? = null

    private val binding get() = _binding!!

    private lateinit var notificationViewModel: NotificationViewModel

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = NotificationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val confirmAddNotification = binding.confirmAddNotificationButton
        confirmAddNotification.setOnClickListener {
            findNavController().popBackStack()
            sharedViewModel.selectedNotifications.value = notificationViewModel.selectedNotifications.value?.map { it.toString() }
        }

        val recyclerView = binding.notificationRecyclerView

        notificationViewModel =
            ViewModelProvider(requireActivity()).get(NotificationViewModel::class.java)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        val adapter = NotificationAdapter(notificationViewModel) {}.apply {
            submitList(notificationViewModel.allNotifications.value)
        }
        recyclerView.adapter = adapter

        notificationViewModel.notificationCounter.observe(viewLifecycleOwner, Observer { counter ->
            binding.notificationCount.text = "(${counter})"
        })

        notificationViewModel.selectedNotifications.observe(viewLifecycleOwner, Observer { notifications ->
            adapter.notifyDataSetChanged()
        })

        NotificationCenter.scheduleNotification(requireContext(), requireActivity(), NotificationTime.AT_DEADLINE, LocalDateTime.now())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
