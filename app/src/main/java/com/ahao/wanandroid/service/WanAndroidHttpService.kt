package com.ahao.wanandroid.service

import com.ahao.wanandroid.WanAndroidHttpDao
import com.ahao.wanandroid.bean.request.LoginRequest
import com.ahao.wanandroid.bean.response.JsonResult
import io.reactivex.Observable

class WanAndroidHttpService {

    fun login(request: LoginRequest) : Observable<JsonResult<String>> {
       return getDao(WanAndroidHttpDao::class.java).login(request)
    }

    fun <T> getDao(clazz: Class<T>) : T{
        return HttpManager.getDao(clazz)
    }
}