package com.ahao.wanandroid.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.ahao.wanandroid.R

class BorderLayout : FrameLayout{

    private var borderColor: Int = 0
    private var borderWidth: Float = 0.toFloat()
    private var radius: Float = 0.toFloat()
    private var drawStyle: Paint.Style? = null

    private var paint: Paint = Paint()
    private var rect: RectF = RectF()

    constructor(context:Context):this(context,null)

    constructor(context: Context, attrs: AttributeSet?):this(context,attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr :Int):super(context,attrs,defStyleAttr){
        setWillNotDraw(false)
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BorderLayout)
        borderWidth = typedArray.getDimension(R.styleable.BorderLayout_borderWidth, 0f)
        radius = typedArray.getDimension(R.styleable.BorderLayout_borderRadius, 0f)
        borderColor = typedArray.getColor(R.styleable.BorderLayout_borderColor, Color.WHITE)
        drawStyle = if (typedArray.getColor(R.styleable.BorderLayout_drawStyle, 1) == 1) Paint.Style.STROKE else Paint.Style.FILL

        typedArray.recycle()

        paint = Paint().apply {
            isAntiAlias = true
            style = drawStyle
            color = borderColor
        }
        rect = RectF()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.style = drawStyle
        paint.color = borderColor

        if (drawStyle == Paint.Style.FILL) {
            rect.apply {
                left = 0f
                top = 0f
                bottom = measuredHeight.toFloat()
                right = measuredWidth.toFloat()
            }
        } else {
            if (borderWidth > 0) {
                paint.strokeWidth = borderWidth
                rect.apply {
                    left = borderWidth / 2
                    top = borderWidth / 2
                    bottom = measuredHeight -  borderWidth / 2
                    right = measuredWidth - borderWidth / 2
                }
            }
        }
        canvas.drawRoundRect(rect, radius, radius, paint)
    }
}