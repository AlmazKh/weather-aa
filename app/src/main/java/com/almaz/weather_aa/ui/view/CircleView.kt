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

    private var animValue = 0
    private val strokeWidth = 90f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var rectF: RectF
    private val sizeInDp = 160f
    private val sizeInPx = convertDpToPixel(sizeInDp, context)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
        paint.color = Color.BLUE

        setRectangle()

        paint.color = Color.YELLOW

        canvas.drawArc(rectF, 0  +animValue.toFloat(), 20f, false, paint)
        canvas.drawArc(rectF, 45 + animValue.toFloat(), 20f, false, paint)
        canvas.drawArc(rectF, 90 + animValue.toFloat(), 20f, false, paint)
        canvas.drawArc(rectF, 135 + animValue.toFloat(), 20f, false, paint)
        canvas.drawArc(rectF, 180 + animValue.toFloat(), 20f, false, paint)
        canvas.drawArc(rectF, 225+ animValue.toFloat(), 20f, false, paint)
        canvas.drawArc(rectF, 270+ animValue.toFloat(), 20f, false, paint)
        canvas.drawArc(rectF, 315+ animValue.toFloat(), 20f, false, paint)
//        canvas.drawArc(rectF, 320+ animValue.toFloat(), 20f, false, paint)

//        canvas.drawArc(rectF, 0  +animValue.toFloat(), 20f, false, paint)
//        canvas.drawArc(rectF, 51 + animValue.toFloat(), 20f, false, paint)
//        canvas.drawArc(rectF, 102 + animValue.toFloat(), 20f, false, paint)
//        canvas.drawArc(rectF, 153 + animValue.toFloat(), 20f, false, paint)
//        canvas.drawArc(rectF, 204 + animValue.toFloat(), 20f, false, paint)
//        canvas.drawArc(rectF, 255+ animValue.toFloat(), 20f, false, paint)
//        canvas.drawArc(rectF, 306+ animValue.toFloat(), 20f, false, paint)
//        canvas.drawArc(rectF, 357+ animValue.toFloat(), 20f, false, paint)

        drawCircle(canvas)
    }


    private fun drawCircle(canvas: Canvas) {
        paint.color = Color.YELLOW
        paint.style = Paint.Style.FILL

        val radius = 80f

        canvas.drawCircle(sizeInPx / 2f, sizeInPx / 2f, radius, paint)
    }

    private fun setRectangle() {
        rectF = RectF(strokeWidth, strokeWidth, width-strokeWidth, height - strokeWidth)
    }

    fun setValue(animatedValue: Int) {
        animValue = animatedValue
        invalidate()
    }

    fun convertDpToPixel(dp: Float, context: Context): Float {
        val f = dp * (context.resources
            .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        return f
    }
}
