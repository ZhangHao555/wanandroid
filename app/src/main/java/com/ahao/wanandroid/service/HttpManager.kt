package com.ahao.wanandroid.service

import com.ahao.wanandroid.API
import com.ahao.wanandroid.BaseUrl
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object HttpManager {
    val daoMap = HashMap<String, Any?>()

    fun <T> getDao(clazz: Class<T>): T {
        if (daoMap[clazz.simpleName] == null) {
            daoMap[clazz.simpleName] = createDao(clazz)
        }
        return daoMap[clazz.simpleName] as T
    }

    private fun <T> createDao(clazz: Class<T>): T{
        val annotation = clazz.getAnnotation(BaseUrl::class.java)
        val baseUrl = annotation?.value ?: API.BASE_URL

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(clazz)
    }


}