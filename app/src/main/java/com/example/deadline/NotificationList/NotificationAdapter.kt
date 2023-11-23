package com.example.deadline.NotificationList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.deadline.data.NotificationTime
import com.example.deadline.databinding.NotificaitonItemBinding

class NotificationAdapter(private val onItemClicked: (NotificationTime) -> Unit) :
    ListAdapter<NotificationTime, NotificationAdapter.NotificationViewHolder>(DiffCallback) {
    class NotificationViewHolder(private val binding: NotificaitonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: NotificationTime) {
            binding.notificationTime.text = notification.displayText
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
        holder.bind(getItem(position))
    }



    override fun getItemCount(): Int {
        return currentList.size
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<NotificationTime>() {
            override fun areItemsTheSame(oldItem: NotificationTime, newItem: NotificationTime): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: NotificationTime, newItem: NotificationTime): Boolean {
                return oldItem == newItem
            }
        }
    }
}