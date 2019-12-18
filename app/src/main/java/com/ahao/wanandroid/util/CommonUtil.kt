package com.ahao.wanandroid.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.ahao.wanandroid.App

fun Activity.hideStatusArea() {
    val decorView = window.decorView
    // Hide the status bar.
    val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
    decorView.systemUiVisibility = uiOptions
}

fun Activity.setStatusBarColor(color: Int) {
    val group = window.decorView as ViewGroup
    group.getChildAt(group.childCount - 1).setBackgroundColor(color)
}

fun Activity.getColorInActivity(colorId: Int) = ContextCompat.getColor(this, colorId)

fun getColorWithApp(colorId: Int) = ContextCompat.getColor(App.context, colorId)

fun isNetWorkConnected() = (App.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null

