package com.ahao.wanandroid.util

import android.app.Activity
import android.content.Context
import android.view.View

fun hideStatusArea(activity: Activity){
    val decorView = activity.window.decorView
    // Hide the status bar.
    val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
    decorView.systemUiVisibility = uiOptions
}

