package com.ahao.wanandroid.util

import android.content.Context.MODE_PRIVATE
import com.ahao.wanandroid.App

object CacheUtil {
    const val USER_CACHE: String = "USER_CACHE"
    const val GLOBAL_CACHE: String = "GLOBAL_CACHE"


    fun cacheString(key: String, value: String) =
        App.context?.getSharedPreferences(USER_CACHE, MODE_PRIVATE)!!.edit().putString(key, value).apply()

    fun getCachedString(key: String) = App.context?.getSharedPreferences(USER_CACHE, MODE_PRIVATE)!!.getString(key, "")


}