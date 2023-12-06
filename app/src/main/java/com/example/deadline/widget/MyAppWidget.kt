package com.example.deadline.widget

import android.content.Context
import android.graphics.Color.parseColor
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.deadline.ProjectDeadlineApplication
import com.example.deadline.data.database.Deadline
import com.example.deadline.data.database.deadlineDateTimeString
import java.util.concurrent.Executors

class MyAppWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        // Load data needed to render the AppWidget.
        // Use `withContext` to switch to another thread for long running
        // operations.

        provideContent {
            // create your AppWidget here
            MyContent()
        }
    }

    @Composable
    private fun MyContent() {
        val context = LocalContext.current
        val application = context.applicationContext as ProjectDeadlineApplication
        val dao = application.database.deadlineDao()
        val deadlines = remember {
            mutableStateOf(emptyList<Deadline>())
        }
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            val deadlinesFour = dao.getFour()
            Handler(Looper.getMainLooper()).post {
                deadlines.value = deadlinesFour
            }
        }


        Column(
            horizontalAlignment = Alignment.Start,
            modifier = GlanceModifier.fillMaxSize()
                .background(color = Color.White)
                .cornerRadius(16.dp)
                .padding(16.dp)
        ) {
            Text(
                text = "Deadlines",
                style = TextStyle(
                    color = ColorProvider(Color.Black),
                    fontWeight = FontWeight.Bold,
                    fontSize = 21.sp,
                ),
                modifier = GlanceModifier
                    .fillMaxWidth()
            )
            deadlines.value.forEach {
                Row(
                    modifier = GlanceModifier.padding(top = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = it.title,
                        style = TextStyle(
                            color = ColorProvider(Color(parseColor(it.color))),
                            fontWeight = FontWeight.Medium,
                            fontSize = 17.sp,
                        ),
                        modifier = GlanceModifier
                            .padding(end = 8.dp)
                    )
                    Spacer(
                        modifier = GlanceModifier
                            .defaultWeight()
                    )
                    Text(
                        text = it.deadlineDateTimeString(),
                        style = TextStyle(
                            color = ColorProvider(Color.Black),
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                        ),
                    )
                }
            }
        }
    }
}