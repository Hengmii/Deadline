package com.example.deadline.view.DeadlineList

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Outline
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.deadline.data.database.Deadline
import com.example.deadline.databinding.DeadlineItemBinding
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime

class DeadlineAdapter(private val onItemClicked: (Deadline) -> Unit) :
    ListAdapter<Deadline, DeadlineAdapter.DeadlineViewHolder>(DiffCallback) {
    var currentPressedPosition: Int? = null
    inner class DeadlineViewHolder(private val binding: DeadlineItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnCreateContextMenuListener { menu, v, menuInfo ->
                currentPressedPosition = adapterPosition
                menu.add(0, 1, 0, "Show in full")
                menu.add(0, 2, 0, "Complete")
                menu.add(0, 3, 0, "Edit")
            }
        }
        @SuppressLint("SetTextI18n")
        @ExperimentalTime
        fun bind(deadline: Deadline) {
            binding.deadlineTitle.text = deadline.title
            binding.root.clipToOutline = true
            binding.root.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    outline.setRoundRect(0, 0, view.width, view.height, 16f)
                }
            }
            try {
                var duration = deadline.deadlineTime.toLong() - System.currentTimeMillis()
                val wholeDuration = deadline.deadlineTime.toLong() - deadline.startTime.toLong()
                val progress = 1 - (duration.toFloat() / wholeDuration.toFloat())

                binding.deadlineProgressBar.setProgress(progress)

                val days = TimeUnit.MILLISECONDS.toDays(duration)
                duration -= TimeUnit.DAYS.toMillis(days)

                val hours = TimeUnit.MILLISECONDS.toHours(duration)
                duration -= TimeUnit.HOURS.toMillis(hours)

                val minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
                duration -= TimeUnit.MINUTES.toMillis(minutes)

                if (days > 0) {
                    binding.deadlineTimeLeft.text = "$days days, $hours hours, $minutes minutes"
                } else if (hours > 0) {
                    binding.deadlineTimeLeft.text = "$hours hours, $minutes minutes"
                } else if (minutes > 0) {
                    binding.deadlineTimeLeft.text = "$minutes minutes"
                } else {
                    binding.deadlineTimeLeft.text = "The deadline has passed"
                }
                val color = Color.parseColor(deadline.color)
                binding.deadlineProgressBar.setProgressColor(color)
            } catch (e: Exception) {
                binding.deadlineTimeLeft.text = "0"
            } catch (e: IllegalArgumentException) {
                binding.deadlineProgressBar.setBackgroundColor(Color.GRAY)
            }

//            binding.deadlineProgressBar.setProgress(deadline.progress)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeadlineViewHolder {
        val viewHolder = DeadlineViewHolder(
            DeadlineItemBinding.inflate(
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

    @OptIn(ExperimentalTime::class)
    override fun onBindViewHolder(holder: DeadlineViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener{
            holder.itemView.showContextMenu()
            true
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun getDeadlineAtPosition(position: Int): Deadline {
        return getItem(position)
    }


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Deadline>() {
            override fun areItemsTheSame(oldItem: Deadline, newItem: Deadline): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Deadline, newItem: Deadline): Boolean {
                return oldItem == newItem
            }
        }
    }
}