package com.example.deadline.NotificationList

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.deadline.R
import com.example.deadline.data.NotificationTime
import com.example.deadline.databinding.NotificaitonItemBinding
import com.example.deadline.viewmodels.NotificationViewModel

class NotificationAdapter(
    private val notificationViewModel: NotificationViewModel,
    private val onItemClicked: (NotificationTime) -> Unit
) :
    ListAdapter<NotificationTime, NotificationAdapter.NotificationViewHolder>(DiffCallback) {
    inner class NotificationViewHolder(private val binding: NotificaitonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            notification: NotificationTime,
            onItemClicked: (NotificationTime) -> Unit,
            viewModel: NotificationViewModel
        ) {
            binding.notificationTime.text = notification.displayText

            updateUIBasedOnClickedState(notification)

            itemView.setOnClickListener {
                notification.toggleClicked()
                viewModel.updateClickedNotification(notification)
                notifyItemChanged(adapterPosition)
                onItemClicked(notification)
            }
        }
        private fun updateUIBasedOnClickedState(notification: NotificationTime) {
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
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClicked(getItem(position))
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClicked, notificationViewModel)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<NotificationTime>() {
            override fun areItemsTheSame(
                oldItem: NotificationTime,
                newItem: NotificationTime
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: NotificationTime,
                newItem: NotificationTime
            ): Boolean {
                return oldItem == newItem && oldItem.isClicked == newItem.isClicked
            }
        }
    }
}