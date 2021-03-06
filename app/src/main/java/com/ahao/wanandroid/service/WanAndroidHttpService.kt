package com.ahao.wanandroid.service

import com.ahao.wanandroid.bean.request.LoginRequest
import com.ahao.wanandroid.bean.request.RegisterRequest
import com.ahao.wanandroid.bean.response.JsonResult
import com.ahao.wanandroid.bean.response.MainPageListResponse
import com.ahao.wanandroid.bean.response.UserInfo
import com.ahao.wanandroid.dao.WanAndroidHttpDao
import com.google.gson.JsonElement
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

class WanAndroidHttpService {

    fun login(request: LoginRequest): Observable<JsonResult<UserInfo>> {
        return getDao(WanAndroidHttpDao::class.java).login(request.username, request.password)
    }

    fun register(request: RegisterRequest): Observable<JsonResult<JsonElement>> {
        return getDao(WanAndroidHttpDao::class.java).register(request.username, request.password, request.repassword)
    }

    suspend fun getMainPageList(pageIndex: Int) :JsonResult<MainPageListResponse>? = withContext(Dispatchers.IO) {
        getDao(WanAndroidHttpDao::class.java).getMainPageList(pageIndex).execute().body()
    }

    private fun <T> getDao(clazz: Class<T>): T {
        return HttpManager.getDao(clazz)
    }
}