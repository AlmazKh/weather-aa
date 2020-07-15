package com.almaz.weather_aa.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var animValue = 0
    private val strokeWidth = 70f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var rectF: RectF
    private lateinit var rectF2: RectF
    private val size = 200f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
        paint.color = Color.BLUE

        setRectangle()

        paint.color = Color.GREEN

        canvas.drawArc(rectF, animValue.toFloat(), 10f, false, paint)

//        drawCircle(canvas)
    }


    private fun drawCircle(canvas: Canvas) {
        paint.color = Color.YELLOW
        paint.style = Paint.Style.FILL

        val radius = 50f

        canvas.drawCircle(size / 2f, size / 2f, radius, paint)
    }

    private fun setRectangle() {
        rectF = RectF(width - 140f, height - 190f, width-100f, height - 10f)
    }

    fun setValue(animatedValue: Int) {
        animValue = animatedValue
        invalidate()
    }
}
