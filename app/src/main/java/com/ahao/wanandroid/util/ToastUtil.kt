package com.ahao.wanandroid.util

import android.widget.Toast
import com.ahao.wanandroid.App

object ToastUtil {
    fun toast(text: String) {
        Toast.makeText(App.context, text, Toast.LENGTH_SHORT).show()
    }

    fun toast(id: Int) {
        Toast.makeText(App.context, id, Toast.LENGTH_SHORT).show()
    }
}