package com.ahao.wanandroid.service

import com.ahao.wanandroid.WanAndroidHttpDao
import com.ahao.wanandroid.bean.request.LoginRequest
import java.util.*
import kotlin.collections.HashMap

object ServiceManager {
    private val services: HashMap<String, Any?> = HashMap()

    fun <T> getService(clazz: Class<T>): T? {
        if(services[clazz.simpleName] == null){
            services[clazz.simpleName] = clazz.newInstance()
        }else services[clazz.simpleName] as T?
        return clazz.newInstance()
    }




}