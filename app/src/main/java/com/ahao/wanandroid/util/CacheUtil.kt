package com.ahao.wanandroid.util

import android.content.Context.MODE_PRIVATE
import com.ahao.wanandroid.App
import com.google.gson.Gson
import java.lang.Exception
import java.lang.reflect.Type

object CacheUtil {
    const val USER_CACHE: String = "USER_CACHE"
    const val GLOBAL_CACHE: String = "GLOBAL_CACHE"
    val gson = Gson()

    fun cacheString(key: String, value: String) =
            App.context?.getSharedPreferences(USER_CACHE, MODE_PRIVATE)!!.edit().putString(key, value).apply()

    fun getCachedString(key: String) = App.context?.getSharedPreferences(USER_CACHE, MODE_PRIVATE)!!.getString(key, "")

    fun cacheObject(key: String, value: Any) {
        cacheString(key, gson.toJson(value))
    }

    fun <T> getCachedObject(key: String, typeOfT:Type): T?{
        return try {
            val cachedString = getCachedString(key)
            gson.fromJson(cachedString,typeOfT)
        } catch (e: Exception) {
            null
        }
    }

    fun getCachedObject(key: String, clazz: Class<*>): Any? {
        return try {
            val cachedString = getCachedString(key)
            gson.fromJson(cachedString, clazz)
        } catch (e: Exception) {
            null
        }
    }

}