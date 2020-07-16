package com.almaz.weather_aa.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View

class CircleView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val strokeWidthInDp = 25f
    private val strokeWidthInPx = convertDpToPixel(strokeWidthInDp)

    private val sizeInDp = 160f
    private val sizeInPx = convertDpToPixel(sizeInDp)

    private val radiusInDp = 30f
    private val radiusInPx = convertDpToPixel(radiusInDp)

    private var animValue = 0f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var rectF: RectF
    private val sweepAngle = 10f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidthInPx
        paint.color = Color.BLUE

        setRectangle()

        paint.color = Color.parseColor("#FFEB3B")

        canvas.drawArc(rectF, 0 + animValue, sweepAngle, false, paint)
        canvas.drawArc(rectF, 45 + animValue, sweepAngle, false, paint)
        canvas.drawArc(rectF, 90 + animValue, sweepAngle, false, paint)
        canvas.drawArc(rectF, 135 + animValue, sweepAngle, false, paint)
        canvas.drawArc(rectF, 180 + animValue, sweepAngle, false, paint)
        canvas.drawArc(rectF, 225 + animValue, sweepAngle, false, paint)
        canvas.drawArc(rectF, 270 + animValue, sweepAngle, false, paint)
        canvas.drawArc(rectF, 315 + animValue, sweepAngle, false, paint)

        drawCircle(canvas)
    }

    private fun drawCircle(canvas: Canvas) {
        paint.color = Color.parseColor("#FFEB3B")
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 25f

        canvas.drawCircle(sizeInPx / 2f, sizeInPx / 2f, radiusInPx, paint)
    }

    private fun setRectangle() {
        rectF = RectF(
            strokeWidthInPx,
            strokeWidthInPx,
            width - strokeWidthInPx,
            height - strokeWidthInPx
        )
    }

    fun setValue(animatedValue: Int) {
        animValue = animatedValue + 0f
        invalidate()
    }

    private fun convertDpToPixel(dp: Float): Float {
        return dp * (context.resources
            .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}
