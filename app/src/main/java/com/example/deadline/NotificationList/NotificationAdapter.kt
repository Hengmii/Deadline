package com.example.deadline.NotificationList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.deadline.R
import com.example.deadline.databinding.NotificaitonItemBinding
import com.example.deadline.viewmodels.NotificationViewModel

class NotificationAdapter(
    private val notificationViewModel: NotificationViewModel,
    private val onItemClicked: (Notification) -> Unit
) :
    ListAdapter<Notification, NotificationAdapter.NotificationViewHolder>(DiffCallback) {
    inner class NotificationViewHolder(private val binding: NotificaitonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            notification: Notification,
            onItemClicked: (Notification) -> Unit,
            viewModel: NotificationViewModel
        ) {
            binding.notificationTime.text = notification.notificationTime.displayText

            updateUIBasedOnClickedState(notification)

            itemView.setOnClickListener {
                viewModel.onClickNotification(notification)
                notifyItemChanged(adapterPosition)
                onItemClicked(notification)
            }
        }
        private fun updateUIBasedOnClickedState(notification: Notification) {
            if (notification.isClicked) {
                binding.addNotificationButton.setBackgroundResource(R.drawable.ic_checkmark)
            } else {
                binding.addNotificationButton.setBackgroundResource(R.drawable.ic_add_black_24dp)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val viewHolder = NotificationViewHolder(
            NotificaitonItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        return viewHolder
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClicked, notificationViewModel)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Notification>() {
            override fun areItemsTheSame(
                oldItem: Notification,
                newItem: Notification
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Notification,
                newItem: Notification
            ): Boolean {
                return oldItem.notificationTime == newItem.notificationTime && oldItem.isClicked == newItem.isClicked
            }
        }
    }
}