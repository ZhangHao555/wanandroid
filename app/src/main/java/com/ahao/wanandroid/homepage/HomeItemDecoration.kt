package com.ahao.wanandroid.homepage

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class HomeItemDecoration : RecyclerView.ItemDecoration() {

    val color = Color.GRAY

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.adapter == null) {
            return
        }
        if (parent.getChildAdapterPosition(view) == (parent.adapter!!.itemCount - 1)) {
            outRect.set(0, 0, 0, 1)
        } else {
            outRect.set(0, 0, 0, 0)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        paint.color = color

        for (i in 0 until parent.childCount) {
            val childAt = parent.getChildAt(i)
            c.drawLine(childAt.left.toFloat(), (childAt.bottom + 1).toFloat(), childAt.right.toFloat(), (childAt.bottom + 1).toFloat(),paint)
        }
    }

}