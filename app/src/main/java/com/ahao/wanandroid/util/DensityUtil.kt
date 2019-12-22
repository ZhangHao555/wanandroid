package com.ahao.wanandroid.util

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import androidx.core.content.ContextCompat.getSystemService
import com.ahao.wanandroid.App

fun getDisplayMetrics(context: Context): DisplayMetrics {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val metrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(metrics)
    return metrics
}

fun getDisplayMetrics(): DisplayMetrics {
    val windowManager = App.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val metrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(metrics)
    return metrics
}

fun dp2px( dpValue: Float): Int {
    val scale = App.context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun px2dp(context: Context, pxValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun getSp(textSize : Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,textSize, getDisplayMetrics())
