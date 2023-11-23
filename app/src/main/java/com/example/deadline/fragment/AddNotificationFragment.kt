package com.example.deadline.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.deadline.databinding.NotificaitonItemBinding

class AddNotificationFragment : Fragment() {
    private var _binding: NotificaitonItemBinding? = null

    private val binding get() = _binding!!

    private var notificationCounter = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = NotificaitonItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addNotificationIconButton = binding.addNotificationIcon
        addNotificationIconButton.setOnClickListener {
            notificationCounter++
        }
    }
}