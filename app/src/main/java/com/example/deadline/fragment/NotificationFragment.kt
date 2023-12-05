package com.example.deadline.fragment

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
import com.example.deadline.NotificationList.NotificationAdapter
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

        scheduleNotification(NotificationTime.AT_DEADLINE, LocalDateTime.now())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    val CHANNEL_ID = "channel_id_example_01"

    fun scheduleNotification(notificationTime: NotificationTime, deadline: LocalDateTime) {
        createNotificationChannel()

        var builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Much longer text that cannot fit one line...")
            )
            .setWhen(System.currentTimeMillis() + 3000)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(requireContext())) {
            // notificationId is a unique int for each notification that you must define.
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )

                return
            }
            notify(1, builder.build())
        }
    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(CHANNEL_ID, "channel_name_example_01", importance).apply {
                    description = "description_example_01"
                }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
