package com.ahao.wanandroid.view

import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

open class ScrollViewPager(context: Context,  attrs: AttributeSet? = null) : ViewPager(context,attrs){

    var canScroll = true

/*
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if(canScroll) super.onInterceptTouchEvent(ev) else false
    }
*/

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return if(canScroll) super.onTouchEvent(ev) else false
    }

}