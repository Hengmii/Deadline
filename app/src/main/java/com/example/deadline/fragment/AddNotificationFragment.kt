package com.example.deadline.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.deadline.databinding.NotificaitonItemBinding
import com.example.deadline.viewmodels.NotificationViewModel

class AddNotificationFragment : Fragment() {
    private var _binding: NotificaitonItemBinding? = null

    private val binding get() = _binding!!

    private val notificationViewModel = NotificationViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = NotificaitonItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addNotificationIconButton = binding.addNotificationButton

        addNotificationIconButton.setOnClickListener {
            Log.d("AddNotificationFragment", "Add notification button clicked")
        }
    }
}