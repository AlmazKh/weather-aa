package com.almaz.weather_aa.ui.weather.states

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.min


class CloudView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var cloudColor = Color.LTGRAY
    private var cloudSize = 120

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCloud(canvas)
    }

    private fun drawCloud(canvas: Canvas) {
        paint.color = cloudColor
        paint.style = Paint.Style.FILL

        canvas.drawCircle(cloudSize * 0.20f, cloudSize * 0.60f, cloudSize * 0.15f, paint)
        canvas.drawCircle(cloudSize * 0.45f, cloudSize * 0.45f, cloudSize * 0.20f, paint)
        canvas.drawCircle(cloudSize * 0.75f, cloudSize * 0.55f, cloudSize * 0.20f, paint)
        canvas.drawCircle(cloudSize * 0.35f, cloudSize * 0.65f, cloudSize * 0.10f, paint)
        canvas.drawCircle(cloudSize * 0.50f, cloudSize * 0.65f, cloudSize * 0.10f, paint)
        canvas.drawCircle(cloudSize * 0.65f, cloudSize * 0.65f, cloudSize * 0.10f, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        cloudSize = min(measuredWidth, measuredHeight)
        setMeasuredDimension(cloudSize, cloudSize)
    }
}
