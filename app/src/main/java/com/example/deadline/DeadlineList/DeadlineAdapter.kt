import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.deadline.data.database.Deadline
import com.example.deadline.databinding.DeadlineItemBinding

class DeadlineAdapter(private val onItemClicked: (Deadline) -> Unit) :
    ListAdapter<Deadline, DeadlineAdapter.DeadlineViewHolder>(DiffCallback) {
    class DeadlineViewHolder(private val binding: DeadlineItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(deadline: Deadline) {
            binding.deadlineTitle.text = deadline.title
            try {
                binding.deadlineTimeLeft.text = (deadline.deadline.toLong() - deadline.start.toLong()).toString()
            } catch (e: Exception) {
                binding.deadlineTimeLeft.text = "0"
            }

//            binding.deadlineProgressBar.setProgressColor(deadline.color.toArgb())
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

    override fun onBindViewHolder(holder: DeadlineViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return currentList.size
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