package com.ahao.wanandroid.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import kotlin.math.max

class TagLayout(context: Context, attributeSet: AttributeSet) : ViewGroup(context, attributeSet) {

    val VERTICAL = 1
    val HORIZONTAL = 2

    var orientation: Int = HORIZONTAL

    var singleLine = false

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        (0 until childCount).map {
            getChildAt(it)
        }.forEach {
            if (it.visibility != View.GONE) {
                measureChildWithMargins(it, widthMeasureSpec, 0, heightMeasureSpec, 0)
            }
        }

        var widthResult = 0
        var heightResult = 0
        var singleLineMaxHeight = 0
        if (orientation == HORIZONTAL) {
            widthResult = MeasureSpec.getSize(widthMeasureSpec)
            var offset = 0
            (0 until childCount).forEach {
                val lp = getChildAt(it).layoutParams as? MarginLayoutParams
                if (offset + getWidthWithMargin(getChildAt(it), lp) > widthResult) {
                    heightResult += singleLineMaxHeight
                    singleLineMaxHeight = 0
                    offset = 0
                }

                offset += getWidthWithMargin(getChildAt(it), lp)
                singleLineMaxHeight = max(singleLineMaxHeight, getHeightWithMargin(getChildAt(it), lp))
                heightResult = max(heightResult, singleLineMaxHeight)

            }
        }

        setMeasuredDimension(widthResult + paddingLeft + paddingRight, heightResult + paddingTop + paddingBottom)
    }

    private fun getWidthWithMargin(v: View, lp: MarginLayoutParams?): Int {
        return if (lp != null) {
            v.measuredWidth + lp.leftMargin + lp.rightMargin
        } else {
            v.measuredWidth
        }
    }

    private fun getHeightWithMargin(v: View, lp: MarginLayoutParams?): Int {
        return if (lp != null) {
            v.measuredHeight + lp.topMargin + lp.bottomMargin
        } else {
            v.measuredHeight
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (!changed) {
            return
        }

        var xOffset = paddingLeft
        var yOffset = paddingTop

        var maxHeight = 0

        (0 until childCount)
                .map { getChildAt(it) }
                .forEach {
                    val lp = it.layoutParams as MarginLayoutParams
                    xOffset += lp.leftMargin

                    maxHeight = max(maxHeight, getHeightWithMargin(it, lp))
                    if (xOffset + getWidthWithMargin(it, lp) > measuredWidth - paddingLeft - paddingRight) {
                        yOffset += getHeightWithMargin(it, lp)
                        xOffset = paddingLeft + lp.leftMargin
                    }

                    it.layout(xOffset, yOffset + lp.topMargin, xOffset + it.measuredWidth, yOffset + it.measuredHeight + lp.topMargin)
                    xOffset += it.measuredWidth + lp.rightMargin
                }

    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean {
        return p is LayoutParams
    }

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams): ViewGroup.LayoutParams {
        return LayoutParams(lp)
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return LayoutParams(context, attrs)
    }

    class LayoutParams : MarginLayoutParams {
        constructor(width: Int, height: Int) : super(width, height)
        constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
        constructor(layoutParams: ViewGroup.LayoutParams) : super(layoutParams)
    }
}