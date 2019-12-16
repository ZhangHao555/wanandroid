package com.ahao.wanandroid.util

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

fun Activity.hideStatusArea(){
    val decorView = window.decorView
    // Hide the status bar.
    val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
    decorView.systemUiVisibility = uiOptions
}
fun Activity.setStatusBarColor(color:Int){
    val group = window.decorView as ViewGroup
    group.getChildAt(group.childCount - 1).setBackgroundColor(color)
}

fun Activity.getColorInActivity(colorId:Int) = ContextCompat.getColor(this,colorId)
