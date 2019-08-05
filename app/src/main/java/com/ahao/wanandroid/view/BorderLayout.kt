package com.ahao.wanandroid.view

import android.content.Context
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.FrameLayout

class BorderLayout : FrameLayout{

    private var borderColor: Int = 0
    private var borderWidth: Float = 0.toFloat()
    private var radius: Float = 0.toFloat()
    private var drawStyle: Paint.Style? = null

    private val paint: Paint = Paint()
    private val rect: RectF = RectF()

    constructor(context:Context):this(context,null){

    }

    constructor(context: Context, attrs: AttributeSet?):super(context,attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr :Int):super(context,attrs,defStyleAttr)
}