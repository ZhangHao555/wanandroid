package com.ahao.wanandroid.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

fun getDisplayMetrics(context: Context): DisplayMetrics {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val metrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(metrics)
    return metrics
}