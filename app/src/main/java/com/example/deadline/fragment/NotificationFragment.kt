package com.example.deadline.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.deadline.NotificationList.NotificationAdapter
import com.example.deadline.data.NotificationTime
import com.example.deadline.databinding.NotificationFragmentBinding


class NotificationFragment : Fragment() {
    private var _binding: NotificationFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = NotificationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val confirmAddNotification = binding.confirmAddNotificationButton
        confirmAddNotification.setOnClickListener {
            val action = NotificationFragmentDirections.actionNotificationFragmentToAddDeadlineFragment()
            findNavController().navigate(action)
        }

        val recyclerView = binding.notificationRecyclerView
        val adapter = NotificationAdapter {}.apply {
            submitList(NotificationTime.values().toList())
        }
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
