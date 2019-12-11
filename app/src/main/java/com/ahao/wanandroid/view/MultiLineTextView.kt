package com.ahao.wanandroid.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.TextView


class MultiLineTextView(context: Context,attributeSet: AttributeSet) : TextView(context,attributeSet) {

    override fun onDraw(canvas: Canvas?) {
        calculateLines()
        super.onDraw(canvas)
    }

    private fun calculateLines() {
        val mHeight = measuredHeight
        val lHeight = lineHeight
        val lines = mHeight / lHeight
        setLines(lines)
    }
}