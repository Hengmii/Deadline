package com.example.deadline.DeadlineList

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.deadline.R

class DeadlineProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var progress = 0f
    private val paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.purple_500)
        strokeWidth = 10f
    }

    fun setProgressColor(color: Int) {
        paint.color = color
        invalidate()
    }

    fun setProgress(progress: Float) {
        this.progress = progress
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val height = height
        val progressHeight = height * progress
        canvas.drawLine(width / 2f, height.toFloat(), width / 2f, height - progressHeight, paint)
    }
}
