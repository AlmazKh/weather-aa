package com.almaz.weather_aa.ui.weather.states

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class RainDropView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var dropColor = Color.BLUE

    private var dropSize = 50
    private val dropPath = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.color = dropColor
        paint.style = Paint.Style.FILL
        dropPath.moveTo(dropSize * 0.5f, dropSize * 0.1f)
        dropPath.quadTo(dropSize * 0.75f, dropSize * 0.6f, dropSize * 0.5f, dropSize * 0.9f)
        dropPath.quadTo(dropSize * 0.25f, dropSize * 0.6f, dropSize * 0.5f, dropSize * 0.1f)
        canvas.drawPath(dropPath, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        dropSize = min(measuredWidth, measuredHeight)
        setMeasuredDimension(dropSize, dropSize)
    }
}
