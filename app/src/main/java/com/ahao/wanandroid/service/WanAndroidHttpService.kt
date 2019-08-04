package com.ahao.wanandroid.service

import com.ahao.wanandroid.WanAndroidHttpDao
import com.ahao.wanandroid.bean.request.LoginRequest
import com.ahao.wanandroid.bean.response.JsonResult
import com.google.gson.JsonObject
import io.reactivex.Observable

class WanAndroidHttpService {

    fun login(request: LoginRequest) : Observable<JsonResult<JsonObject>>{
       return getDao(WanAndroidHttpDao::class.java).login(request)
    }

    fun <T> getDao(clazz: Class<T>) : T{
        return HttpManager.getDao(clazz)
    }
}