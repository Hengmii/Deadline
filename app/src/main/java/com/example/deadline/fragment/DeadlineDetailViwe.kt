package com.example.deadline.fragment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Database
import com.example.deadline.data.database.AppDatabase
import com.example.deadline.data.database.Deadline

@Composable
fun DeadlineDetailView(deadline: Deadline) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = deadline.startTime,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Text(
                    text = deadline.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = deadline.deadlineTime,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
            Divider(color = Color.LightGray, thickness = 1.dp)
            // Placeholder for your countdown timer
            Text(
                text = "Time left",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
            // Placeholder for notifications area
            Text(
                text = "Notifications",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "There are no notifications for this deadline yet",
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            // Placeholder for quick note area
            Text(
                text = "Quick note",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
            Button(
                onClick = { /* TODO: Add your note adding logic here */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(text = "Add a note")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { /* TODO: Complete action */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Complete")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = { /* TODO: Edit action */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Edit")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDeadlineDetailScreen() {
    // 创建一个示例 Deadline 对象
    val ddl = Deadline(
        title = "Deadline Title",
        start = "1701808416256",
        startTime = "1701808416256",
        deadline = "1701808516256",
        deadlineTime = "1701808516256",
        color = "#FF0000",
        notification = "10",
        state = "Not Started"
    )
    DeadlineDetailView(ddl)
}
