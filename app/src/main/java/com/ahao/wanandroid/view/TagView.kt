package com.ahao.wanandroid.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.TextView
import com.ahao.wanandroid.R

class TagView(context: Context, attrs: AttributeSet?) : TextView(context, attrs) {
    constructor(context: Context) : this(context, null)

    var borderColor: Int = 0
    var borderWidth: Float = 0.toFloat()
    var radius: Float = 0.toFloat()
    var drawStyle: Paint.Style

    private var paint: Paint
    private var rect: RectF

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TagView)
        borderWidth = typedArray.getDimension(R.styleable.TagView_borderWidth, 0f)
        radius = typedArray.getDimension(R.styleable.TagView_borderRadius, 0f)
        borderColor = typedArray.getColor(R.styleable.TagView_borderColor, Color.WHITE)
        drawStyle = if (typedArray.getColor(R.styleable.TagView_drawStyle, 1) == 1) Paint.Style.STROKE else Paint.Style.FILL
        typedArray.recycle()

        paint = Paint()
        paint.isAntiAlias = true

        rect = RectF()
    }

    override fun onDraw(canvas: Canvas) {
        paint.color = borderColor
        paint.style = drawStyle

        if (drawStyle == Paint.Style.FILL) {
            rect.left = 0f
            rect.top = 0f
            rect.bottom = measuredHeight.toFloat()
            rect.right = measuredWidth.toFloat()
        } else {
            if (borderWidth > 0) {
                paint.strokeWidth = borderWidth
                rect.left = borderWidth / 2
                rect.top = borderWidth / 2
                rect.bottom = measuredHeight - borderWidth / 2
                rect.right = measuredWidth - borderWidth / 2
            }
        }
        canvas.drawRoundRect(rect, radius, radius, paint)
        super.onDraw(canvas)
    }
}
