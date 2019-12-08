package com.ahao.wanandroid.homepage

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ahao.wanandroid.util.dp2px


class HomeItemDeractor : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.adapter == null) {
            return
        }
        outRect.set(0, 0,0, 20)
        if (parent.getChildAdapterPosition(view) == (parent.adapter!!.itemCount - 1)) {
            outRect.set(0, 0,0, 0)
        } else {
            outRect.set(0,0,0,dp2px(10f))
        }
    }

}