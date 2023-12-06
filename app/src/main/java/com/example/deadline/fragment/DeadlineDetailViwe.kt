package com.example.deadline.fragment

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.text.style.TextAlign.Companion.End
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import com.example.deadline.ProjectDeadlineApplication
import com.example.deadline.data.DeadlineState
import com.example.deadline.data.database.Deadline
import com.example.deadline.data.database.deadlineDateTimeString
import com.example.deadline.data.database.notificationTimes
import com.example.deadline.data.database.remainDayHourMinuteSecond
import com.example.deadline.data.database.startDateTimeString
import com.example.deadline.viewmodels.ProjectDeadlineViewModel
import com.example.deadline.viewmodels.ProjectDeadlineViewModelFactory
import kotlinx.coroutines.delay
import java.util.concurrent.Executors

@Composable
fun DeadlineDetailView(deadline: Deadline, completeHandler: () -> Unit, backHandler: () -> Unit) {
    val remainDays = remember { mutableStateOf(0L) }
    val remainHours = remember { mutableStateOf(0L) }
    val remainMinutes = remember { mutableStateOf(0L) }
    val remainSeconds = remember { mutableStateOf(0L) }

    val marginHorizontal = 16.dp

    LaunchedEffect(Unit) {
        while (true) {
            val remainDayHourMinuteSecond = deadline.remainDayHourMinuteSecond()
            remainDays.value = remainDayHourMinuteSecond[0]
            remainHours.value = remainDayHourMinuteSecond[1]
            remainMinutes.value = remainDayHourMinuteSecond[2]
            remainSeconds.value = remainDayHourMinuteSecond[3]
            delay(1000L) // 等待一秒
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(
                        color = Color(parseColor("#F7F7F7"))
                    )
                    .padding(marginHorizontal)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Done",
                            fontSize = 17.sp,
                            textAlign = End,
                            fontWeight = FontWeight.Bold,
                            color = Color(parseColor("#555555")),
                            modifier = Modifier
                                .padding(marginHorizontal)
                                .clickable {
                                    backHandler()
                                }
                        )
                    }
                    Text(
                        text = deadline.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp,
                        textAlign = Center,
                        modifier = Modifier
                            .padding(64.dp)
                            .fillMaxWidth()
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                text = "${deadline.startDateTimeString()}",
                                fontSize = 16.sp,
                            )
                            Text(
                                text = "Start",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light,
                                color = Color.Gray
                            )
                        }
                        Column {
                            Text(
                                text = "${deadline.deadlineDateTimeString()}",
                                fontSize = 16.sp,
                            )
                            Text(
                                text = "Deadline",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light,
                                color = Color.Gray,
                                modifier = Modifier.align(Alignment.End)
                            )
                        }
                    }
                }
            }
            Column(modifier = Modifier.padding(marginHorizontal)) {
                Text(
                    text = "Time left",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    if (remainDays.value + remainHours.value + remainMinutes.value + remainSeconds.value < 0L
                    ) {
                        Text(
                            text = "The deadline has passed",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Text(
                                text = remainDays.value.toString(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "Days",
                                fontSize = 16.sp,
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Text(
                                text = remainHours.value.toString(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "Hours",
                                fontSize = 16.sp,
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Text(
                                text = remainMinutes.value.toString(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "Minutes",
                                fontSize = 16.sp,
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Text(
                                text = remainSeconds.value.toString(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "Seconds",
                                fontSize = 16.sp,
                            )
                        }
                    }
                }
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Text(
                        text = "Notifications",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Light,
                    )
                    val notificationTimes = deadline.notificationTimes()
                    if (notificationTimes.isNotEmpty()) {
                        Column(modifier = Modifier.padding(top = 8.dp)) {
                            deadline.notificationTimes()
                                .forEach { time ->
                                    Text(
                                        text = time.displayText,
                                        fontSize = 16.sp,
                                        textAlign = Center,
                                        modifier = Modifier
                                            .padding(horizontal = 8.dp)
                                            .padding(vertical = 2.dp)
                                            .fillMaxWidth()
                                    )
                                }
                        }
                    } else {
                        Text(
                            text = "There are no notifications for this deadline yet",
                            color = Color.Gray,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .padding(top = 64.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            completeHandler()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(parseColor("#E7E7E7"))
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Complete",
                            color = Color.Black
                        )
                    }
//                    Spacer(modifier = Modifier.width(16.dp))
//                    Button(
//                        onClick = { /* TODO: Edit action */ },
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color(parseColor("#E7E7E7"))
//                        ),
//                        modifier = Modifier
//                            .weight(1f)
//                    ) {
//                        Text(
//                            text = "Edit",
//                            color = Color.Black,
//                        )
//                    }
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
        deadline = "1701808416256",
        deadlineTime = "1701908516256",
        color = "#FF0000",
        notification = "ONE_MONTH_BEFORE,ONE_WEEK_BEFORE,ONE_DAY_BEFORE",
        state = "Not Started"
    )
    DeadlineDetailView(ddl, {}, {})
}
