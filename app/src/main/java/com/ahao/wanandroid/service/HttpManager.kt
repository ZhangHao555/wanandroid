@file:Suppress("UNCHECKED_CAST")

package com.ahao.wanandroid.service

import com.ahao.wanandroid.API
import com.ahao.wanandroid.BaseUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object HttpManager {
    val daoMap = HashMap<String, Any?>()

    fun <T> getDao(clazz: Class<T>): T {
        if (daoMap[clazz.simpleName] == null) {
            daoMap[clazz.simpleName] = createDao(clazz)
        }
        return daoMap[clazz.simpleName] as T
    }

    private fun <T> createDao(clazz: Class<T>): T {
        val annotation = clazz.getAnnotation(BaseUrl::class.java)
        val baseUrl = annotation?.value ?: API.BASE_URL

        val client = OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
        }
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(clazz)
    }


}