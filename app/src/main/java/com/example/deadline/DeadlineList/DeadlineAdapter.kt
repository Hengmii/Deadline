import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.graphics.toArgb
import androidx.recyclerview.widget.RecyclerView
import com.example.deadline.DeadlineList.DeadlineProgressBar
import com.example.deadline.R
import com.example.deadline.data.Deadline

class MyAdapter(private val deadlineList: List<Deadline>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.deadline_title)
        val timeLeft: TextView = itemView.findViewById(R.id.deadline_time_left)
        val progressBar: DeadlineProgressBar = itemView.findViewById(R.id.progressBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.deadline_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = deadlineList[position]
        holder.title.text = currentItem.title

        val totalDuration = currentItem.deadline.time - currentItem.start.time
        val timeLeft = currentItem.deadline.time - System.currentTimeMillis()
        val progress = if (totalDuration != 0L) timeLeft.toFloat() / totalDuration else 0f

        val diffInHours = timeLeft / (60 * 60 * 1000)
        val diffInMins = (timeLeft / (60 * 1000)) % 60
        holder.timeLeft.text = "${diffInHours}hr, ${diffInMins}min"

        holder.progressBar.setProgressColor(currentItem.color.toArgb())
        holder.progressBar.setProgress(progress)
    }

    override fun getItemCount() = deadlineList.size
}
