package com.example.deadline.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.deadline.NotificationList.NotificationAdapter
import com.example.deadline.data.NotificationTime
import com.example.deadline.databinding.NotificationFragmentBinding
import com.example.deadline.viewmodels.NotificationViewModel


class NotificationFragment : Fragment() {
    private var _binding: NotificationFragmentBinding? = null

    private val binding get() = _binding!!

    private lateinit var notificationViewModel: NotificationViewModel

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
            val action =
                NotificationFragmentDirections.actionNotificationFragmentToAddDeadlineFragment()
            findNavController().navigate(action)
        }

        val recyclerView = binding.notificationRecyclerView

        notificationViewModel =
            ViewModelProvider(requireActivity()).get(NotificationViewModel::class.java)

        val adapter = NotificationAdapter(notificationViewModel) {}.apply {
            submitList(NotificationTime.values().toList().sortedByDescending { it.offset })
        }
        recyclerView.adapter = adapter

        notificationViewModel.notificationCounter.observe(viewLifecycleOwner, Observer { counter ->
            binding.notificationCount.text = "(${counter})"
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
