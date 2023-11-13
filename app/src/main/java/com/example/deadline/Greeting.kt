package com.example.deadline

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import java.util.Calendar
import com.vdurmont.emoji.EmojiManager
import kotlin.random.Random

@Composable
@Preview
fun Greeting() {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val allEmojis = EmojiManager.getAll().toList()
    val randomEmoji = allEmojis[Random.nextInt(allEmojis.size)].unicode
    Text(when (currentHour) {
        in 0 .. 20 -> "$randomEmoji Hello"
        else -> "🌙Good night!"
    })
}