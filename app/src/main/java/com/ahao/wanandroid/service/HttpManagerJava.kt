@file:Suppress("UNCHECKED_CAST")

package com.ahao.wanandroid.service

import com.ahao.wanandroid.API
import com.ahao.wanandroid.BaseUrl
import java.util.HashMap

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object HttpManagerJava {

    private val daoMap = HashMap<String, Any?>()

    @Suppress("UNCHECKED_CAST")
    fun <T> getDao(clazz: Class<T>): T? {
        if (daoMap[clazz.simpleName] == null) {
            daoMap[clazz.simpleName] = createDao(clazz)
        }
        return daoMap[clazz.simpleName] as T?
    }

    private fun <T> createDao(clazz: Class<T>): T? {
        val annotation = clazz.getAnnotation(BaseUrl::class.java)
        val baseUrl = if (annotation == null) "" else API.BASE_URL
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(clazz)
    }


}
