@file:Suppress("UNCHECKED_CAST")

package com.ahao.wanandroid.service

import kotlin.collections.HashMap

object ServiceManager {
    private val services: HashMap<String, Any?> = HashMap()

    fun <T> getService(clazz: Class<T>): T? {
        if (services[clazz.simpleName] == null) {
            services[clazz.simpleName] = clazz.newInstance()
        }
        return services[clazz.simpleName] as T?
    }


}