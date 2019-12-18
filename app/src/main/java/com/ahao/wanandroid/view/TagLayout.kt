package com.ahao.wanandroid.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import kotlin.math.max

class TagLayout(context: Context, attributeSet: AttributeSet) : ViewGroup(context, attributeSet) {

    val VERTICAL = 1
    val HORIZONTAL = 2

    var orientation: Int = HORIZONTAL

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        var widthResult: Int
        var heightResult = 0
        if (orientation == HORIZONTAL) {
            widthResult = MeasureSpec.getSize(widthMeasureSpec)
            var offset = 0
            var maxHeight = 0
            (0 until childCount).forEach {
                maxHeight = max(maxHeight, getChildAt(it).measuredHeight)
                if (offset + getChildAt(it).measuredWidth > widthResult) {
                    heightResult += maxHeight
                } else {
                    offset += getChildAt(it).measuredWidth
                }
            }
        }
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }
}