package com.ahao.wanandroid.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.ahao.wanandroid.R
import com.ahao.wanandroid.util.dp2px
import com.ahao.wanandroid.util.getColorWithApp
import kotlinx.android.synthetic.main.view_title_layout.view.*

class TitleBar(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {

    var leftClickListener: (() -> Unit)? = null
    var rightClickListener: (() -> Unit)? = null
    var centerClickListener: (() -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_title_layout, this, true)

        defaultTitleBar()
    }

    private fun defaultTitleBar() {
        left_container.removeAllViews()
        center_container.removeAllViews()
        right_container.removeAllViews()
        left_container.addView(ImageView(context).apply {
            setPadding(dp2px(10f), dp2px(0f), dp2px(10f), dp2px(0f))
            setImageResource(R.mipmap.back_arrow)
            scaleType = ImageView.ScaleType.FIT_XY
        })
    }

    fun setCenterView(title: String, listener: (() -> Unit)? = null) {
        center_container.removeAllViews()
        center_container.addView(TextView(context).apply {
            textSize = 15f
            setTextColor(getColorWithApp(R.color.white))
            text = title
            gravity = Gravity.CENTER
        })
        center_container.setOnClickListener {
            listener?.invoke()
        }

    }

    fun setCenterView(view: View, layoutWidth: Int = ViewGroup.LayoutParams.MATCH_PARENT, layoutHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT) {
        center_container.removeAllViews()
        center_container.addView(view, layoutWidth, layoutHeight)
    }

    fun setRightView(view: View, layoutWidth: Int = ViewGroup.LayoutParams.WRAP_CONTENT, layoutHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT) {
        right_container.removeAllViews()
        right_container.addView(view, layoutWidth, layoutHeight)
    }
}