package com.example.firstlab.customView

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.firstlab.R
import com.example.firstlab.models.Emotion
import com.example.firstlab.models.EmotionType

class MoodColumnView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 12.dpToPx()
        textAlign = Paint.Align.CENTER
        typeface = ResourcesCompat.getFont(context, R.font.vela_sans_bold)
    }
    var emotionsList: List<Pair<EmotionType, Float>> =
        listOf(
            Pair(EmotionType.RED, 0.25f),
            Pair(EmotionType.GREEN, 0.5f),
            Pair(EmotionType.BLUE, 0.25f)
        )
    var colors = intArrayOf()

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var top = 0f
        var currentHeight = 0f

        if (emotionsList.isEmpty()) {
            paint.apply {
                color = ContextCompat.getColor(context, R.color.navbarBackground)
            }

            canvas.drawRoundRect(
                0f,
                top,
                width.toFloat(),
                height.toFloat(),
                8.dpToPx(),
                8.dpToPx(),
                paint
            )
        } else {
            emotionsList.forEach { index ->
                currentHeight += height.toFloat() * index.second
                colors = getGradientColors(index.first)
                paint.apply {
                    shader = LinearGradient(
                        0f,
                        top,
                        width.toFloat(),
                        currentHeight,
                        colors,
                        null,
                        Shader.TileMode.CLAMP
                    )
                }

                canvas.drawRoundRect(
                    0f,
                    top,
                    width.toFloat(),
                    currentHeight,
                    8.dpToPx(),
                    8.dpToPx(),
                    paint
                )
                canvas.drawText(
                    "${(index.second * 100).toInt()}%",
                    width / 2f,
                    (currentHeight - (currentHeight - top) / 2f),
                    textPaint
                )
                top = currentHeight + 4.dpToPx()
            }
        }
    }

    fun setEmotionList(emotions: List<Pair<EmotionType, Float>>) {
        emotionsList = emotions
    }

    private fun getGradientColors(type: EmotionType): IntArray {
        return when (type) {
            EmotionType.BLUE ->
                intArrayOf(
                    ContextCompat.getColor(context, R.color.blueEmoteFirst),
                    ContextCompat.getColor(context, R.color.blueEmoteSecond)
                )

            EmotionType.GREEN ->
                intArrayOf(
                    ContextCompat.getColor(context, R.color.greenEmoteFirst),
                    ContextCompat.getColor(context, R.color.greenEmoteSecond)
                )

            EmotionType.RED ->
                intArrayOf(
                    ContextCompat.getColor(context, R.color.redEmoteFirst),
                    ContextCompat.getColor(context, R.color.redEmoteSecond),
                )

            EmotionType.YELLOW ->
                intArrayOf(
                    ContextCompat.getColor(context, R.color.yellowEmoteFirst),
                    ContextCompat.getColor(context, R.color.yellowEmoteSecond)
                )

        }
    }

    private fun Int.dpToPx(): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, toFloat(), Resources.getSystem().displayMetrics
        )
    }
}